package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.OmsOrder;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/21 | 10:11
 **/
public interface OrderService {

    String checkTradeCode(String memberId , String tradeCode);

    String genTradeCode(String memberId);

    void SaveOrderAndDeletCartItem(OmsOrder omsOrder);

    OmsOrder getOrderByOutTradeNumber(String outTradeNumber);

    void updataOrder(OmsOrder omsOrder);
}
