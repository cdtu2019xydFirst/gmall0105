package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

public class PmsProductSaleAttrValue implements Serializable {


    @Id
    @Column
    private String id;
    @Column
    private String productId;
    @Column
    private String saleAttrId;
    @Column
    private String saleAttrValueName;
    @Transient
    private String isChecked;

    public PmsProductSaleAttrValue() {
    }

    public PmsProductSaleAttrValue(String id, String productId, String saleAttrId, String saleAttrValueName, String isChecked) {
        this.id = id;
        this.productId = productId;
        this.saleAttrId = saleAttrId;
        this.saleAttrValueName = saleAttrValueName;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(String saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = saleAttrValueName;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
