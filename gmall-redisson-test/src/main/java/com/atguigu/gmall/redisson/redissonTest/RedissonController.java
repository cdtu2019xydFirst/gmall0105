package com.atguigu.gmall.redisson.redissonTest;

import com.atguigu.gmall.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @author : 熊亚东
 * @description :基于redisson锁的高并发解决方案测试
 * @date : 2019/7/7 | 7:30
 **/
@Controller
public class RedissonController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("testRedisson")
    @ResponseBody
    public String testRedisson() {

        Jedis jedis = redisUtil.getJedis();
        /*声明锁*/
        RLock mylock = redissonClient.getLock("key"); // key字符串随便取
        /*加锁*/
            mylock.lock(); // lock提供带timeout参数，timeout结束强制解锁，防止死锁
            String v = jedis.get("k");
            if (StringUtils.isEmpty(v)) {
                v = "1";
            }
            System.out.println("--->>" + v);
            jedis.set("k", (Integer.parseInt(v) + 1) + "");
            jedis.close();
            /*解锁*/
            mylock.unlock();
        return "test success";
    }
}
