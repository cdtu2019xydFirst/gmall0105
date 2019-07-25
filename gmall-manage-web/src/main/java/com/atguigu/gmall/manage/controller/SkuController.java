package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class SkuController {

    @Reference
    SkuService skuService;

    @RequestMapping("skuList")
    @ResponseBody
    public List<PmsSkuInfo> skuList(String catalog3Id){
        List<PmsSkuInfo> PmsSkuInfo = skuService.skuList(catalog3Id);
        return PmsSkuInfo;
    }

    //保存Sku
    @RequestMapping("SavePmsSkuInfo")
    @ResponseBody
    public String SavePmsSkuInfo(PmsSkuInfo pmsSkuInfo){

        skuService.SavePmsSkuInfo(pmsSkuInfo);
            return "Save Sku Success!";
    }

}
