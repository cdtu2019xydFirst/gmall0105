package com.atguigu.gmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.OmsOrder;
import com.atguigu.gmall.bean.OmsOrderItem;
import com.atguigu.gmall.mq.ActiveMQUtil;
import com.atguigu.gmall.order.mapper.OmsOrderItemMapper;
import com.atguigu.gmall.order.mapper.OmsOrderMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/21 | 10:17
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RedisUtil redisUtil;

    @Reference
    CartService cartService;/*这个使得order——service变成既是提供者又是消费者，消费CartService服务*/

    @Autowired
    OmsOrderMapper omsOrderMapper;

    @Autowired
    OmsOrderItemMapper omsOrderItemMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {/*判断交易码*/
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            String tradeKey = "User:" + memberId + ":tradeCode";
            //String tradeCodeFromCache = jedis.get(tradeKey);
            /*lua脚本 防止黑客攻击*/
            String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            Long eval = (Long) jedis.eval(script, Collections.singletonList(tradeKey), Collections.singletonList(tradeCode));
            /*在这里一个用户可能在多地址登录几乎同时来到这里，这时程序还没来得及执行下面语句就有多个相同的trdaeCode同时返回success，造成OrderController多次提交订单
             * 所以，这里我们可以使用lua脚本，当发现"User:" + memberId + ":tradeCode"，就立即删除*/
            if (eval != null && eval != 0) {
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public String genTradeCode(String memberId) {/*得到交易码*/

        Jedis jedis = null;
        String tradeCode = null;
        try {
            jedis = redisUtil.getJedis();
            tradeCode = UUID.randomUUID().toString();
            jedis.setex("User:" + memberId + ":tradeCode", 60 * 15, tradeCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
            return tradeCode;
        }
    }

    @Override
    public void SaveOrderAndDeletCartItem(OmsOrder omsOrder) {/*保存订单*/
        /*保存订单表*/
        omsOrderMapper.insertSelective(omsOrder);
        String orderId = omsOrder.getId();/*主键返回策略==========================================*/
        /*保存订单详情*/
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(orderId);
            omsOrderItemMapper.insertSelective(omsOrderItem);
            /*删除购物车数据*/
            /*cartService.deletByOrderItemProductSkuId(omsOrderItem.getProductSkuId());*/
        }
    }

    @Override
    public OmsOrder getOrderByOutTradeNumber(String outTradeNumber) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(outTradeNumber);
        OmsOrder omsOrder1 = omsOrderMapper.selectOne(omsOrder);
        return omsOrder1;
    }

    @Override
    public void updataOrder(OmsOrder omsOrder) {
        Example example = new Example(OmsOrder.class);
        example.createCriteria().andEqualTo("orderSn" , omsOrder.getOrderSn());
        OmsOrder omsOrder1 = new OmsOrder();
        omsOrder1.setStatus("1");/*1，代表已支付*/
        Connection connection = null;
        Session session = null;
        try {
            connection = activeMQUtil.getConnectionFactory().createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            omsOrderMapper.updateByExampleSelective(omsOrder1 , example);
            /*订单跟新完毕后 ,给库存发送一个订单已支付的队列*/
            /*支付成功后 引起的系统服务 ，——> 订单服务更新 ，——> （当前）库存服务 ，——> 物流服务*/
            /*=================================这里我们将引入分布式事务消息中间件ActiveMQ================================*/
            /*调用ActiveMQ发送支付成功的消息*/
            Queue order_payed_queue = session.createQueue("ORDER_PAYED_QUEUE");/*创建队列模式消息*/
            MessageProducer producer = session.createProducer(order_payed_queue);
            //ActiveMQTextMessage textMessage = new ActiveMQTextMessage();/*字符串形式结构的消息*/
            ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();/*hash形式结构的消息（K   V）结构*/

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
