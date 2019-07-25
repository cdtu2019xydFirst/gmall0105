package com.atguigu.gmall.payment.mq;

import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.Date;
import java.util.Map;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/24 | 9:55
 **/
@Component/*让springboot启动时扫描此类*/
public class PaymentServiceMqListener {

    @Autowired
    PaymentService paymentService;

    @JmsListener(destination = "PAYMENT_CHECK_QUEUE", containerFactory = "jmsQueueListener")
    public void consumPaymentCheckResult(MapMessage mapMessage) throws JMSException {
        String out_trade_no = mapMessage.getString("out_trade_no");
        Integer count = 0;
        if (mapMessage.getString("count") != null){
            count = Integer.parseInt("" + mapMessage.getInt("count"));
        }
        /*==================================================================================================================================*/
                                              /*调用阿里的蚂蚁金服的支付查询接口，在PaymentServiceImpl实现*/
        /*==================================================================================================================================*/
        Map<String, Object> resultMap = paymentService.checkAlipayPayment(out_trade_no);
        if (resultMap == null || resultMap.isEmpty()) {
            /*继续发送延时消息检查用户支付状态*/
            if (count > 0){
                count--;
                System.out.println("检查次数剩余"+count);
                paymentService.sendDelayPaymentResultCheckQueue(out_trade_no, count);
            }else {
                System.out.println("支付失败");
                return ;
            }
        } else {
            String trade_status = (String) resultMap.get("trade_status");
            /*根据从alipay查询的支付状态结果，判断是否进行下一次的延迟任务还是支付成功更新数据和后续任务*/
            System.err.println(trade_status);
            if (StringUtils.isNotBlank(trade_status) && trade_status.equals("TRADE_SUCCESS")) {
                /*支付成功，更新支付发送支付成功消息队列*/
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setOrderSn(out_trade_no);
                paymentInfo.setPaymentStatus("已付款");
                paymentInfo.setAlipayTradeNo((String) resultMap.get("trade_no"));
                paymentInfo.setCallbackContent((String) resultMap.get("callback_content"));
                paymentInfo.setCallbackTime(new Date());
                paymentInfo.setConfirmTime(new Date());
                /*======================================================================================================================================*/
                /*进行支付更新的幂等性检查操作在updatePayment方法里面，防止与paymentController一起重复更新*/
                /*======================================================================================================================================*/
                paymentService.updatePayment(paymentInfo);
                return ;
            } else {
                /*继续发送延时消息检查用户支付状态*/
                if (count > 0){
                    count--;
                    System.out.println("检查次数剩余"+count);
                    paymentService.sendDelayPaymentResultCheckQueue(out_trade_no, count);
                }else {
                    System.out.println("支付失败");
                    return ;
                }
            }
        }
    }

}
