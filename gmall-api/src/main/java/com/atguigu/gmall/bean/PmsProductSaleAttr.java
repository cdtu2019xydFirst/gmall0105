package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsProductSaleAttr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String productId;
    @Column
    private String saleAttrId;
    @Column
    private String saleAttrName;
    @Transient
    private List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList;

    public PmsProductSaleAttr() {
    }

    public PmsProductSaleAttr(String id, String productId, String saleAttrId, String saleAttrName, List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList) {
        this.id = id;
        this.productId = productId;
        this.saleAttrId = saleAttrId;
        this.saleAttrName = saleAttrName;
        this.pmsProductSaleAttrValueList = pmsProductSaleAttrValueList;
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

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName;
    }

    public List<PmsProductSaleAttrValue> getPmsProductSaleAttrValueList() {
        return pmsProductSaleAttrValueList;
    }

    public void setPmsProductSaleAttrValueList(List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList) {
        this.pmsProductSaleAttrValueList = pmsProductSaleAttrValueList;
    }
}
