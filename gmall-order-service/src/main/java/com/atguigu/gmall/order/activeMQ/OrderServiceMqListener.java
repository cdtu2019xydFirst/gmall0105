package com.atguigu.gmall.order.activeMQ;

import com.atguigu.gmall.bean.OmsOrder;
import com.atguigu.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/23 | 15:34
 **/
@Component
public class OrderServiceMqListener {

    @Autowired
    OrderService orderService;

    @JmsListener(destination = "PAYMENT_SUCCESS_QUEUE" , containerFactory = "jmsQueueListener")
    public void consumPaymentResult(MapMessage mapMessage) throws JMSException {
        String out_trade_no = mapMessage.getString("out_trade_no");
        /*在PaymentServiceImpl中支付成功后成功更新了paymentInfo的支付状态，发送消息给订单，让其更新支付状态（status）*/
        /*更新订单状态业务*/
        System.out.println(out_trade_no);
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(out_trade_no);
        orderService.updataOrder(omsOrder);/*调用更新业务*/
        System.out.println("ok");
    }
}
