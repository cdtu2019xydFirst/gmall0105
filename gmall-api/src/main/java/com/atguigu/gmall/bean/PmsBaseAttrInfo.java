package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class PmsBaseAttrInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;
    @Column
    private String isEnabled;
    @Transient
    private List<PmsBaseAttrValue> pmsBaseAttrValueList;

    public PmsBaseAttrInfo() {
    }

    public PmsBaseAttrInfo(String attrName, String catalog3Id, String isEnabled, List<PmsBaseAttrValue> pmsBaseAttrValueList) {
        this.attrName = attrName;
        this.catalog3Id = catalog3Id;
        this.isEnabled = isEnabled;
        this.pmsBaseAttrValueList = pmsBaseAttrValueList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<PmsBaseAttrValue> getPmsBaseAttrValueList() {
        return pmsBaseAttrValueList;
    }

    public void setPmsBaseAttrValueList(List<PmsBaseAttrValue> pmsBaseAttrValueList) {
        this.pmsBaseAttrValueList = pmsBaseAttrValueList;
    }
}
