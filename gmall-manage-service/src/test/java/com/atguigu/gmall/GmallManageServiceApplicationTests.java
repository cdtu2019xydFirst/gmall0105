package com.atguigu.gmall;

import com.atguigu.gmall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageServiceApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {

        /*Jedis jedis = redisUtil.getJedis();
        System.out.println(jedis);
        System.out.println("********************redis test successed!**********************");*/
        String token = UUID.randomUUID().toString();
        Jedis jedis = redisUtil.getJedis();
        int skuId = 1;
        /*String lockToken = jedis.set("abc", token, "nx", "px", 30*1000);*/
        String lockToken = jedis.set("skuId" , token);
        String s = jedis.get("skuId");
        System.out.println(s);
    }

}
