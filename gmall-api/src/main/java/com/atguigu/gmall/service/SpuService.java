package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttrValue;

import java.util.List;

public interface SpuService {

    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsProductSaleAttr> spuSaleAttr(String productId);

    List<PmsProductSaleAttrValue> spuSaleAttrValue(PmsProductSaleAttr pmsProductSaleAttr);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId , String skuId);
}
