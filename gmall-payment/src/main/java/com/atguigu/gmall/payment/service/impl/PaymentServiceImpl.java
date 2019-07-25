package com.atguigu.gmall.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.mq.ActiveMQUtil;
import com.atguigu.gmall.payment.mapper.PaymentInfoMapper;
import com.atguigu.gmall.service.PaymentService;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/22 | 17:16
 **/
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentInfoMapper paymentInfoMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Autowired
    AlipayClient alipayClient;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }

    @Override
    public void updatePayment(PaymentInfo paymentInfo) {

        /*======================================================================================================================================*/
        /*进行支付更新的幂等性检查操作在updatePayment方法里面，防止paymentController 与 PaymentServiceMqListener 一起重复更新*/
        /*======================================================================================================================================*/
        PaymentInfo paymentInfo1 = new PaymentInfo();
        paymentInfo1.setOrderSn(paymentInfo.getOrderSn());
        PaymentInfo paymentInfoResult = paymentInfoMapper.selectOne(paymentInfo1);
        if (paymentInfoResult != null && paymentInfoResult.getPaymentStatus().equals("已付款")) {
            /*说明在paymentController 与 PaymentServiceMqListener里面其中一个已经执行了更新操作*/
            return;
        } else {
            Example example = new Example(PaymentInfo.class);
            example.createCriteria().andEqualTo("orderId", paymentInfo.getOrderId());
            Connection connection = null;
            Session session = null;
            try {
                connection = activeMQUtil.getConnectionFactory().createConnection();
                session = connection.createSession(true, Session.SESSION_TRANSACTED);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            try {
                paymentInfoMapper.updateByExampleSelective(paymentInfo, example);
                /*=====================================================================================================================================*/
                /*支付成功后 引起的系统服务 ，——> （当前）订单服务更新 ，——> 库存服务 ，——> 物流服务*/
                /*这里我们将引入分布式事务消息中间件ActiveMQ*//*调用ActiveMQ发送支付成功的消息*/
                /*=====================================================================================================================================*/
                Queue payment_success_queue = session.createQueue("PAYMENT_SUCCESS_QUEUE");/*创建队列模式消息*/
                MessageProducer producer = session.createProducer(payment_success_queue);
                //ActiveMQTextMessage textMessage = new ActiveMQTextMessage();/*字符串形式结构的消息*/
                ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();/*hash形式结构的消息（K   V）结构*/
                mapMessage.setString("out_trade_no", paymentInfo.getOrderSn());
                producer.send(mapMessage);/*发送消息*/
                session.commit();
            } catch (Exception e) {
                /*更新失败，回滚*/
                try {
                    session.rollback();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    connection.close();/*关闭连接*/
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void sendDelayPaymentResultCheckQueue(String outTradeNumber, int count) {
        Connection connection = null;
        Session session = null;
        try {
            connection = activeMQUtil.getConnectionFactory().createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            /*调用ActiveMQ发送支付成功的消息*/
            Queue payment_check_queue = session.createQueue("PAYMENT_CHECK_QUEUE");/*创建队列模式消息*/
            MessageProducer producer = session.createProducer(payment_check_queue);
            //ActiveMQTextMessage textMessage = new ActiveMQTextMessage();/*字符串形式结构的消息*/
            ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();/*hash形式结构的消息（K   V）结构*/
            mapMessage.setString("out_trade_no", outTradeNumber);
            mapMessage.setInt("count", count);
            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000 * 30);/*加入延迟时间*/
            producer.send(mapMessage);/*发送消息*/
            session.commit();
        } catch (Exception e) {
            /*更新失败，回滚*/
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();/*关闭连接*/
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Object> checkAlipayPayment(String out_trade_no) {

        Map<String, Object> resultMap = new HashMap<>();

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("out_trade_no", out_trade_no);
        request.setBizContent(JSON.toJSONString(requestMap));
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
            resultMap.put("out_trade_no", response.getOutTradeNo());
            resultMap.put("trade_no", response.getTradeNo());
            resultMap.put("trade_status", response.getTradeStatus());
            resultMap.put("callback_content", response.getMsg());
        } else {
            System.out.println("有可能交易未创建调用失败");
        }
        return resultMap;
    }
}
