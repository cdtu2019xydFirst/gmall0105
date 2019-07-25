package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PaymentInfo;

import java.util.Map;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/22 | 16:47
 **/
public interface PaymentService {
    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePayment(PaymentInfo paymentInfo);

    void sendDelayPaymentResultCheckQueue(String outTradeNumber , int count);

    Map<String, Object> checkAlipayPayment(String out_trade_no);
}
