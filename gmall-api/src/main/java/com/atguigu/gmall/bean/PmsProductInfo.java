package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsProductInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String productName;
    @Column
    private String description;
    @Column
    private String catalog3Id;
    @Column
    private String tmId;
    @Transient
    private List<PmsProductImage> pmsProductImageList;
    @Transient
    private  List<PmsProductSaleAttr> pmsProductSaleAttrList;

    public PmsProductInfo() {
    }

    public PmsProductInfo(String id, String productName, String description, String catalog3Id, String tmId, List<PmsProductImage> pmsProductImageList, List<PmsProductSaleAttr> pmsProductSaleAttrList) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.catalog3Id = catalog3Id;
        this.tmId = tmId;
        this.pmsProductImageList = pmsProductImageList;
        this.pmsProductSaleAttrList = pmsProductSaleAttrList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getTmId() {
        return tmId;
    }

    public void setTmId(String tmId) {
        this.tmId = tmId;
    }

    public List<PmsProductImage> getPmsProductImageList() {
        return pmsProductImageList;
    }

    public void setPmsProductImageList(List<PmsProductImage> pmsProductImageList) {
        this.pmsProductImageList = pmsProductImageList;
    }

    public List<PmsProductSaleAttr> getPmsProductSaleAttrList() {
        return pmsProductSaleAttrList;
    }

    public void setPmsProductSaleAttrList(List<PmsProductSaleAttr> pmsProductSaleAttrList) {
        this.pmsProductSaleAttrList = pmsProductSaleAttrList;
    }
}
