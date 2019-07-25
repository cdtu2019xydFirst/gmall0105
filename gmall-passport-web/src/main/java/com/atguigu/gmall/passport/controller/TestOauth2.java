package com.atguigu.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.util.HttpclientUtil;

import java.util.Map;

/**
 * @author : 熊亚东
 * @description :授权协议及其实现社交登录功能
 * @date : 2019/7/19 | 17:28
 **/
public class TestOauth2 {

    /*1.2两步合并获得code*/
    public static String getCode(){
        /*3702255538*/
        /*203c233ca28be441bdaf3fabfb82eedb*/
        /*http://passport.gmall.com:8085/vlogin*/
        /*1.通过client_id 以及 redirect_uri，返回给你一个code*/
        String s1 = HttpclientUtil.doGet("https://api.weibo.com/oauth2/authorize?client_id=3702255538&response_type=code&redirect_uri=http://passport.gmall.com:8085/vlogin");
        System.out.println(s1);
        /*注意：在第一步和第二部之间有个用户授权登录过程（前提是之前该用户没有登录过）*/
        /*2.获得Code*/
        /*code: 855c7a61c4771f4a97183f363bdea0ac*/
        String s2 = "http://passport.gmall.com:8085/vlogin?code=a7e0af09cdbeceec24faeb5863f89912";
        return null;
    }

    /*3.通过code获取用户信息以及最重要的access_token*/
    public static Map<String , String> getAccess_token(){
        /*3.通过code获取access_token  , 同时会将用户Id等信息查询出来一并返回给你*/
        /*https://api.weibo.com/oauth2/access_token?client_id=YOU&client_secret=YOU&grant_type=authorization_code&redirect_uri=YOU&code=CODE*/
        String s3 = "https://api.weibo.com/oauth2/access_token?client_id=3702255538&client_secret=203c233ca28be441bdaf3fabfb82eedb&grant_type=authorization_code&redirect_uri=http://passport.gmall.com:8085/vlogin&code=d333b721a62b7c357f4881fb9d33dbaa";
        String access_token_json = HttpclientUtil.doPost(s3, null);
        Map<String , String> access_map = JSON.parseObject(access_token_json, Map.class);
        System.out.println(access_map.get("access_token"));
        System.out.println(access_map.get("uid"));
        return access_map;
    }

    /*4.通过access_token获取用户所有信息*/
    public static Map<String , String> getUser_info(){
        /*4.用access_token查询用户信息*/
        String s4 = "https://api.weibo.com/2/users/show.json?access_token=2.00RKgSuHAnRYCEfb64e515371fhLGE&uid=7244856629";
        String user_json = HttpclientUtil.doGet(s4);
        Map<String , String> user_map = JSON.parseObject(user_json, Map.class);
        System.out.println(user_map);
        return user_map;
    }

    public static void main(String[] args) {
        getUser_info();
    }


}
