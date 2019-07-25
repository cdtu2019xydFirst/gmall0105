package com.atguigu.gmall.seckill.controller;

import com.atguigu.gmall.util.RedisUtil;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author : 熊亚东
 * @description :秒杀
 * @date : 2019/7/25 | 9:23
 **/
@Controller
@CrossOrigin
public class SecKillController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisUtil redisUtil;


    /*先到先得式秒杀*/
    @RequestMapping("killFirst")
    @ResponseBody
    public String killFirst() {
        String memberId = "某某";
        RSemaphore semaphore = redissonClient.getSemaphore("6");
        /*tryAcquire()  ，尝试去执行，可能成功，可能失败*/
        boolean b = semaphore.tryAcquire();
        if (b) {
            System.out.println("当前用户：" + memberId + "抢购成功！！");
            /*用消息队列发送订单消息*/


        } else {
            System.out.println("当前用户：" + memberId + "抢购失败！！");
        }
        return "1";
    }


    /*拼运气秒杀*/
    @RequestMapping("killSecond")
    @ResponseBody
    public String killSecond() {
        String memberId = "某某";
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            /*开启商品的监控*/
            jedis.watch("6");
            Integer key = Integer.parseInt(jedis.get("6"));
            if (key > 0) {
                Transaction multi = jedis.multi();/*开启事务*/
                multi.incrBy("6", -1);
                List<Object> exec = multi.exec();
                if (exec != null && exec.size() > 0) {
                    System.out.println("当前库存剩余数量：" + key + "当前用户：" + memberId + "抢购成功！！");
                    /*用消息队列发送订单消息*/


                } else {
                    System.out.println("当前库存剩余数量：" + key + "当前用户：" + memberId + "抢购失败！！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return "1";
    }
}
