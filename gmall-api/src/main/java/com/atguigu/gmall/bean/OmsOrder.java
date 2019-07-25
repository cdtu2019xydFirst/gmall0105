package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/20 | 14:05
 **/
public class OmsOrder implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String memberId;
    @Column
    private String couponId;
    @Column
    private String orderSn;
    @Column
    private Date createTime;
    @Column
    private String memberUsername;
    @Column
    private String totalAmount;
    @Column
    private String payAmount;
    @Column
    private String freightAmount;
    @Column
    private String promotionAmount;
    @Column
    private String integrationAmount;
    @Column
    private String couponAmount;
    @Column
    private String discountAmount;
    @Column
    private String payType;
    @Column
    private String sourceType;
    @Column
    private String status;
    @Column
    private String orderType;
    @Column
    private String deliveryCompany;
    @Column
    private String deliverySn;
    @Column
    private String autoConfigDay;
    @Column
    private String integration;
    @Column
    private String growth;
    @Column
    private String promotionInfo;
    @Column
    private String billType;
    @Column
    private String billHeader;
    @Column
    private String billContent;
    @Column
    private String bill_receiverPhone;
    @Column
    private String billReceiverEmail;
    @Column
    private String receiverName;
    @Column
    private String receiverPhone;
    @Column
    private String receiverPostCode;
    @Column
    private String receiverProvince;
    @Column
    private String receiverCity;
    @Column
    private String receiverRegion;
    @Column
    private String receiverDetailAddress;
    @Column
    private String note;
    @Column
    private String confirmStatus;
    @Column
    private String deleteStatus;
    @Column
    private String useIntegration;
    @Column
    private Date paymentTime;
    @Column
    private Date deliveryTime;
    @Column
    private Date receiveTime;
    @Column
    private Date commentTime;
    @Column
    private Date modifyTime;
    @Transient
    List<OmsOrderItem> omsOrderItems;

    public OmsOrder() {
    }

    public OmsOrder(String memberId, String couponId, String orderSn, Date createTime, String memberUsername, String totalAmount, String payAmount, String freightAmount, String promotionAmount, String integrationAmount, String couponAmount, String discountAmount, String payType, String sourceType, String status, String orderType, String deliveryCompany, String deliverySn, String autoConfigDay, String integration, String growth, String promotionInfo, String billType, String billHeader, String billContent, String bill_receiverPhone, String billReceiverEmail, String receiverName, String receiverPhone, String receiverPostCode, String receiverProvince, String receiverCity, String receiverRegion, String receiverDetailAddress, String note, String confirmStatus, String deleteStatus, String useIntegration, Date paymentTime, Date deliveryTime, Date receiveTime, Date commentTime, Date modifyTime, List<OmsOrderItem> omsOrderItems) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.orderSn = orderSn;
        this.createTime = createTime;
        this.memberUsername = memberUsername;
        this.totalAmount = totalAmount;
        this.payAmount = payAmount;
        this.freightAmount = freightAmount;
        this.promotionAmount = promotionAmount;
        this.integrationAmount = integrationAmount;
        this.couponAmount = couponAmount;
        this.discountAmount = discountAmount;
        this.payType = payType;
        this.sourceType = sourceType;
        this.status = status;
        this.orderType = orderType;
        this.deliveryCompany = deliveryCompany;
        this.deliverySn = deliverySn;
        this.autoConfigDay = autoConfigDay;
        this.integration = integration;
        this.growth = growth;
        this.promotionInfo = promotionInfo;
        this.billType = billType;
        this.billHeader = billHeader;
        this.billContent = billContent;
        this.bill_receiverPhone = bill_receiverPhone;
        this.billReceiverEmail = billReceiverEmail;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverPostCode = receiverPostCode;
        this.receiverProvince = receiverProvince;
        this.receiverCity = receiverCity;
        this.receiverRegion = receiverRegion;
        this.receiverDetailAddress = receiverDetailAddress;
        this.note = note;
        this.confirmStatus = confirmStatus;
        this.deleteStatus = deleteStatus;
        this.useIntegration = useIntegration;
        this.paymentTime = paymentTime;
        this.deliveryTime = deliveryTime;
        this.receiveTime = receiveTime;
        this.commentTime = commentTime;
        this.modifyTime = modifyTime;
        this.omsOrderItems = omsOrderItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(String freightAmount) {
        this.freightAmount = freightAmount;
    }

    public String getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(String promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(String integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getAutoConfigDay() {
        return autoConfigDay;
    }

    public void setAutoConfigDay(String autoConfigDay) {
        this.autoConfigDay = autoConfigDay;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public String getBill_receiverPhone() {
        return bill_receiverPhone;
    }

    public void setBill_receiverPhone(String bill_receiverPhone) {
        this.bill_receiverPhone = bill_receiverPhone;
    }

    public String getBillReceiverEmail() {
        return billReceiverEmail;
    }

    public void setBillReceiverEmail(String billReceiverEmail) {
        this.billReceiverEmail = billReceiverEmail;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(String useIntegration) {
        this.useIntegration = useIntegration;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<OmsOrderItem> getOmsOrderItems() {
        return omsOrderItems;
    }

    public void setOmsOrderItems(List<OmsOrderItem> omsOrderItems) {
        this.omsOrderItems = omsOrderItems;
    }
}
