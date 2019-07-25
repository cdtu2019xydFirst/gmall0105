package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {

    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){
        List<PmsProductInfo> PmsProductInfo = spuService.spuList(catalog3Id);
        return PmsProductInfo;
    }

    @RequestMapping("spuSaleAttr")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttr(String productId){
        List<PmsProductSaleAttr> PmsProductSaleAttr = spuService.spuSaleAttr(productId);
        return PmsProductSaleAttr;
    }

    @RequestMapping("spuSaleAttrValue")
    @ResponseBody
    public List<PmsProductSaleAttrValue> spuSaleAttrValue(PmsProductSaleAttr pmsProductSaleAttr){
        List<PmsProductSaleAttrValue> PmsProductSaleAttrValue = spuService.spuSaleAttrValue(pmsProductSaleAttr);
        return PmsProductSaleAttrValue;
    }

}
