package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsSkuAttrValue implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String attrId;
    @Column
    private String valueId;
    @Column
    private String skuId;
    @Transient
    List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValue;

    public PmsSkuAttrValue() {
    }

    public PmsSkuAttrValue(String attrId, String valueId, String skuId, List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValue) {
        this.attrId = attrId;
        this.valueId = valueId;
        this.skuId = skuId;
        this.pmsSkuSaleAttrValue = pmsSkuSaleAttrValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public List<PmsSkuSaleAttrValue> getPmsSkuSaleAttrValue() {
        return pmsSkuSaleAttrValue;
    }

    public void setPmsSkuSaleAttrValue(List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValue) {
        this.pmsSkuSaleAttrValue = pmsSkuSaleAttrValue;
    }
}
