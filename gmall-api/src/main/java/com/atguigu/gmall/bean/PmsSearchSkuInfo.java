package com.atguigu.gmall.bean;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/14 | 8:31
 **/
public class PmsSearchSkuInfo implements Serializable {

    @Id
    private String id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private String price;
    private String skuDefaultImg;
    private double hotScore;
    private String productId;
    private List<PmsSkuAttrValue> pmsSkuAttrValue;

    public PmsSearchSkuInfo() {
    }

    public PmsSearchSkuInfo(String id, String skuName, String skuDesc, String catalog3Id, String price, String skuDefaultImg, double hotScore, String productId, List<PmsSkuAttrValue> pmsSkuAttrValue) {
        this.id = id;
        this.skuName = skuName;
        this.skuDesc = skuDesc;
        this.catalog3Id = catalog3Id;
        this.price = price;
        this.skuDefaultImg = skuDefaultImg;
        this.hotScore = hotScore;
        this.productId = productId;
        this.pmsSkuAttrValue = pmsSkuAttrValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public double getHotScore() {
        return hotScore;
    }

    public void setHotScore(double hotScore) {
        this.hotScore = hotScore;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<PmsSkuAttrValue> getPmsSkuAttrValue() {
        return pmsSkuAttrValue;
    }

    public void setPmsSkuAttrValue(List<PmsSkuAttrValue> pmsSkuAttrValue) {
        this.pmsSkuAttrValue = pmsSkuAttrValue;
    }
}
