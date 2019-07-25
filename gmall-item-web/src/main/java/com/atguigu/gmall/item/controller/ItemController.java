package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    @RequestMapping("index")
    public String index(ModelMap modelMap) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("循环数据" + i);
        }
        modelMap.put("list", list);
        modelMap.put("chk", "0");
        modelMap.put("hello", "hello thymeleaf!!");

        return "index";
    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map , HttpServletRequest request) {
        /*1.没有用nginx负载均衡的算法，获取用户的IP地址*/
        String ip = request.getRemoteAddr();
        /*2.使用nginx负载均衡的算法，获取用户的IP地址*/
        String header = request.getHeader("");

        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId , ip);
        /*sku对象*/
        map.put("skuInfo", pmsSkuInfo);
        /*销售属性列表 pmsSkuInfo.getProductId()*/
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(), pmsSkuInfo.getId());
        map.put("skuSaleAttrListCheckBySku", pmsProductSaleAttrs);

        /*查询当前的sku的spu的其他sku集合的hash表*/
        Map<String, String> skuSaleAttrHash = new HashMap<>();
        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());
        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String hashKey = "";
            String hashValue = skuInfo.getId();
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = skuInfo.getPmsSkuSaleAttrValue();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
                hashKey += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";//5|7|9...
            }
            skuSaleAttrHash.put(hashKey, hashValue);
        }
        /*将sku的销售属性hash表放到页面*/
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        map.put("skuSaleAttrHashJsonStr", skuSaleAttrHashJsonStr);

        return "item";
    }
}
