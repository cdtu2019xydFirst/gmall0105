package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : 熊亚东
 * @description :
 * @date : 2019/7/15 | 12:25
 **/
public class PmsSearchParam implements Serializable {

    private String catalog3Id;
    private String keyword;
    private String[] valueId;

    public PmsSearchParam() {
    }

    public PmsSearchParam(String catalog3Id, String keyword, String[] valueId) {
        this.catalog3Id = catalog3Id;
        this.keyword = keyword;
        this.valueId = valueId;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }
}
