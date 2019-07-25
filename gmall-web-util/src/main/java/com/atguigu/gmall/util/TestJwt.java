package com.atguigu.gmall.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/18 | 9:13
 **/
public class TestJwt {

    public static void main(String[] args) {
        Map<String , Object> map = new HashMap<>();
        map.put("memberId" , "1");
        map.put("nickname" , "小三");
        String ip = "127.0.0.1";
        String time = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        String encode = JwtUtil.encode("2019gmall0105", map, ip + time);
        System.err.println(encode);
    }

}
