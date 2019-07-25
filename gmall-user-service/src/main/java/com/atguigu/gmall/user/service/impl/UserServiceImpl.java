package com.atguigu.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.atguigu.gmall.user.mapper.UserMapper;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

//@Service改为dubbo协议的
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    /*用户登录避免查询mysql，造成mysql压力，提前将用户缓存到redis*/
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> memberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        return memberReceiveAddresses;
    }

    @Override
    public List<UmsMember> getUmsMemberById(String id) {
        UmsMember umsMember = new UmsMember();
        umsMember.setId(id);
        List<UmsMember> umsMembers = userMapper.select(umsMember);
        return umsMembers;
    }

    @Override
    public void addUmsMember(UmsMember umsMember) {
        userMapper.insert(umsMember);
    }

    @Override
    public void deletUmsMemberById(String Id) {
        userMapper.deleteByPrimaryKey(Id);
    }

    @Override
    public void updataUmsMember(UmsMember umsMember) {
        userMapper.updateByPrimaryKey(umsMember);
    }

    @Override
    public void addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress) {
        umsMemberReceiveAddressMapper.insert(umsMemberReceiveAddress);
    }

    @Override
    public void deletUmsMemberReceiveAddressById(String Id) {
        umsMemberReceiveAddressMapper.deleteByPrimaryKey(Id);
    }

    @Override
    public void updataUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress1) {
        umsMemberReceiveAddressMapper.updateByPrimaryKey(umsMemberReceiveAddress1);
    }

    @Override/*passport认证中心*/
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if (jedis != null) {
                String umsMemberStr = jedis.get("user:" + umsMember.getPassword() + umsMember.getUsername() + ":info");
                if (StringUtils.isNotBlank(umsMemberStr)) {
                    /*用户名，密码正确*/
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    return umsMemberFromCache;
                } else {
                    /*用户名，密码错误 . 或者缓存中没有 ，访问数据库*/
                    UmsMember umsMemberFromDb = loginFromDb(umsMember);
                    if (umsMemberFromDb != null) {
                        jedis.setex("user:" + umsMember.getPassword() + umsMember.getUsername() + ":info", 60 * 60 * 24, JSON.toJSONString(umsMemberFromDb));
                    }
                    return umsMemberFromDb;
                }
            } else {
                /*redis出错了 ，访问数据库*/
                UmsMember umsMemberFromDb = loginFromDb(umsMember);
                if (umsMemberFromDb != null) {
                    jedis.setex("user:" + umsMember.getPassword() + umsMember.getUsername() + ":info", 60 * 60 * 24, JSON.toJSONString(umsMemberFromDb));
                }
                return umsMemberFromDb;
            }
        } finally {
            jedis.close();
        }
    }

    @Override
    public void addUserToken(String token, String memberId) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            jedis.setex("user:" + memberId + ":token", 60 * 60 * 2, token);
        } finally {
            jedis.close();
        }
    }

    @Override
    public UmsMember addOauthUser(UmsMember umsMember) {
        userMapper.insertSelective(umsMember);
        return umsMember;
    }

    @Override
    public UmsMember checkOauthUser(UmsMember umsCheck) {
        UmsMember umsMember = userMapper.selectOne(umsCheck);
        return umsMember;
    }

    @Override/*获取用户来源 ， 新浪， 腾讯 ， 百度 。。。*/
    public String getSourceType(String memberId) {
        UmsMember umsMember = new UmsMember();
        umsMember.setId(memberId);
        UmsMember member = userMapper.selectOne(umsMember);
        if (member != null) {
            if (member.getSourceType().equals("1")) {
                return "谷粒用户: " + member.getNickname() + " 您好：";
            } else if (member.getSourceType().equals("2")) {
                return "新浪用户: " + member.getNickname() + " 您好：";
            } else if (member.getSourceType().equals("3")) {
                return "腾讯用户: " + member.getNickname() + " 您好：";
            } else {
                return "百度用户: " + member.getNickname() + " 您好：";
            }
        }
        return null;
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressByReceiveAddressId(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(receiveAddressId);
        UmsMemberReceiveAddress receiveAddress = umsMemberReceiveAddressMapper.selectOne(umsMemberReceiveAddress);
        return receiveAddress;
    }

    private UmsMember loginFromDb(UmsMember umsMember) {/*如果数据库出错，查询得到多条相同用户名，密码，为了程序兼容性，修改为List<UmsMember>*/
        List<UmsMember> umsMemberList = userMapper.select(umsMember);
        if (umsMemberList != null) {
            UmsMember umsMember1 = umsMemberList.get(0);/*取多条重复数据的一条*/
            return umsMember1;
        }
        return null;
    }
}
