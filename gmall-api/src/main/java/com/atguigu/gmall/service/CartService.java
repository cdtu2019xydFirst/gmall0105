package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.OmsCartItem;

import java.util.List;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/17 | 13:14
 **/
public interface CartService {
    OmsCartItem ifCartsExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItem);

    void updataCart(OmsCartItem omsCartItemFromDb);

    void flushCartCatch(String memberId);

    List<OmsCartItem> cartList(String memberId);

    void deletByOrderItemProductSkuId(String productSkuId);

    void delCartByMemberid(String memberId);
}
