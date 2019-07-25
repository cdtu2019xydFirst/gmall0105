package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class PmsBaseCatalog2 implements Serializable {

    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String catalog1Id;
    @Transient
    private List<PmsBaseCatalog3> catalog3ss;

    public PmsBaseCatalog2() {
    }

    public PmsBaseCatalog2(String id, String name, String catalog1Id, List<PmsBaseCatalog3> catalog3ss) {
        this.id = id;
        this.name = name;
        this.catalog1Id = catalog1Id;
        this.catalog3ss = catalog3ss;
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

    public String getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(String catalog1Id) {
        this.catalog1Id = catalog1Id;
    }

    public List<PmsBaseCatalog3> getCatalog3ss() {
        return catalog3ss;
    }

    public void setCatalog3ss(List<PmsBaseCatalog3> catalog3ss) {
        this.catalog3ss = catalog3ss;
    }
}
