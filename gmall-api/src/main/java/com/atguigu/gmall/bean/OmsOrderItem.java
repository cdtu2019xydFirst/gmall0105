package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/20 | 14:15
 **/
public class OmsOrderItem implements Serializable {

    @Id
    @Column
    private String id;
    @Column
    private String orderId;
    @Column
    private String orderSn;
    @Column
    private String productId;
    @Column
    private String productPic;
    @Column
    private String productName;
    @Column
    private String productBrand;
    @Column
    private String productSn;
    @Column
    private String productPrice;
    @Column
    private String productQuantity;
    @Column
    private String productSkuId;
    @Column
    private String productSkuCode;
    @Column
    private String productCategoryId;
    @Column
    private String sp1;
    @Column
    private String sp2;
    @Column
    private String sp3;

    public OmsOrderItem() {
    }

    public OmsOrderItem(String id, String orderId, String orderSn, String productId, String productPic, String productName, String productBrand, String productSn, String productPrice, String productQuantity, String productSkuId, String productSkuCode, String productCategoryId, String sp1, String sp2, String sp3) {
        this.id = id;
        this.orderId = orderId;
        this.orderSn = orderSn;
        this.productId = productId;
        this.productPic = productPic;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productSn = productSn;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSkuId = productSkuId;
        this.productSkuCode = productSkuCode;
        this.productCategoryId = productCategoryId;
        this.sp1 = sp1;
        this.sp2 = sp2;
        this.sp3 = sp3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public String getSp3() {
        return sp3;
    }

    public void setSp3(String sp3) {
        this.sp3 = sp3;
    }
}
