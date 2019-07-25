package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/22 | 16:51
 **/
public class PaymentInfo implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String orderSn;
    @Column
    private String orderId;
    @Column
    private String alipayTradeNo;
    @Column
    private String totalAmount;
    @Column
    private String subject;
    @Column
    private String paymentStatus;
    @Column
    private Date createTime;
    @Column
    private Date confirmTime;
    @Column
    private String callbackContent;
    @Column
    private Date callbackTime;

    public PaymentInfo() {
    }

    public PaymentInfo(String id, String orderSn, String orderId, String alipayTradeNo, String totalAmount, String subject, String paymentStatus, Date createTime, Date confirmTime, String callbackContent, Date callbackTime) {
        this.id = id;
        this.orderSn = orderSn;
        this.orderId = orderId;
        this.alipayTradeNo = alipayTradeNo;
        this.totalAmount = totalAmount;
        this.subject = subject;
        this.paymentStatus = paymentStatus;
        this.createTime = createTime;
        this.confirmTime = confirmTime;
        this.callbackContent = callbackContent;
        this.callbackTime = callbackTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCallbackContent() {
        return callbackContent;
    }

    public void setCallbackContent(String callbackContent) {
        this.callbackContent = callbackContent;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }
}
