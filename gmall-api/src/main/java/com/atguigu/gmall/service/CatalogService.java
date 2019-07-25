package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService {

    List<PmsBaseCatalog1> GetCatalog1();

    List<PmsBaseCatalog2> GetCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> GetCatalog3(String catalog2Id);
}
