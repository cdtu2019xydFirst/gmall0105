package com.atguigu.gmall.payment.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/23 | 12:14
 **/
public class WangJianLin {
    public static void main(String[] args) {
        ConnectionFactory connect = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.255.128:61616");
        try {
            Connection connection = connect.createConnection();
            connection.start();
            /*第一个值表示是否使用事务，如果选择true，第二个值相当于选择0*/
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);/*如果没写session.rollback();自动session.commit();*/
            Topic testTopic = session.createTopic("drink");
            MessageConsumer consumer = session.createConsumer(testTopic);
            consumer.setMessageListener(new MessageListener() {/*消息监听器*/
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage){
                        String text = null;
                        try {
                            text = ((TextMessage) message).getText();
                            System.err.println(text+"我来了，我来执行。。。我是王健林");
                        } catch (JMSException e) {
                            try {/*执行失败回滚*/
                                session.rollback();
                            } catch (JMSException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
