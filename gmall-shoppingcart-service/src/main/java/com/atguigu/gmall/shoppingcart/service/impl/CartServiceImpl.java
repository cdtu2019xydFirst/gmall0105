package com.atguigu.gmall.shoppingcart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.shoppingcart.mapper.OmsCartItemMapper;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/17 | 13:17
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public OmsCartItem ifCartsExistByUser(String memberId, String skuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        OmsCartItem omsCartItem1 = omsCartItemMapper.selectOne(omsCartItem);
        return omsCartItem1;
    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        if (StringUtils.isNotBlank(omsCartItem.getMemberId())){
            omsCartItemMapper.insert(omsCartItem);
        }
    }

    @Override
    public void updataCart(OmsCartItem omsCartItemFromDb) {
        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("id" , omsCartItemFromDb.getId());
        omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb , e);
    }

    @Override
    public void flushCartCatch(String memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(omsCartItem);
        /*同步到redis缓存中*/
        Jedis jedis = redisUtil.getJedis();
        /*结构
             K                V
        * memberId  map<skuId , cartItem->JSON>
        * V 用Hashmap<>集合表示,方便修改，更新，查询数据
        * */
        HashMap<String, String> map = new HashMap<>();
        for (OmsCartItem cartItem : omsCartItems) {
            map.put(cartItem.getProductSkuId() , JSON.toJSONString(cartItem));
        }
        jedis.hmset("user:"+memberId+":cart" , map);
        jedis.close();
    }

    @Override
    public List<OmsCartItem> cartList(String memberId) {
        Jedis jedis = null;
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        try{
            jedis = redisUtil.getJedis();/*Hash()查询*/
            List<String> hvals = jedis.hvals("user:" + memberId + ":cart");
            for (String hval : hvals) {
                OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
                omsCartItems.add(omsCartItem);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            jedis.close();
        }
        return omsCartItems;
    }

    @Override
    public void deletByOrderItemProductSkuId(String productSkuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setProductSkuId(productSkuId);
        omsCartItemMapper.deleteByPrimaryKey(omsCartItem);
    }

    @Override
    public void delCartByMemberid(String memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItemMapper.delete(omsCartItem);

        Jedis jedis = null;
        try{
            jedis = redisUtil.getJedis();
            jedis.del("user:"+memberId+":cart");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }

}
