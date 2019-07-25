package com.atguigu.gmall.payment.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/23 | 10:39
 **/
public class TestMq {

    public static void main(String[] args) {
        ConnectionFactory connect = new ActiveMQConnectionFactory("tcp://192.168.255.128:61616");
        try {
            Connection connection = connect.createConnection();
            connection.start();
            /*第一个值表示是否使用事务，如果选择true，第二个值相当于选择0*/
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //Queue testQueue = session.createQueue("drink");/*队列模式消息，只允许一人消费*/
            Topic testTopic = session.createTopic("drink");/*话题模式消息，所有人都消费*/
            MessageProducer producer = session.createProducer(testTopic);
            ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText("我渴了，请给我打一杯水！");
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(textMessage);
            session.commit();/*提交事务*/
            connection.close();/*关闭事务*/
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
