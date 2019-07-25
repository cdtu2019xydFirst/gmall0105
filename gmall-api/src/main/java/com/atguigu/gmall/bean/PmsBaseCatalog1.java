package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class PmsBaseCatalog1 implements Serializable {

    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Transient
    private List<PmsBaseCatalog2> catalog2s;

    public PmsBaseCatalog1() {
    }

    public PmsBaseCatalog1(String id, String name, List<PmsBaseCatalog2> catalog2s) {
        this.id = id;
        this.name = name;
        this.catalog2s = catalog2s;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PmsBaseCatalog2> getCatalog2s() {
        return catalog2s;
    }

    public void setCatalog2s(List<PmsBaseCatalog2> catalog2s) {
        this.catalog2s = catalog2s;
    }
}
