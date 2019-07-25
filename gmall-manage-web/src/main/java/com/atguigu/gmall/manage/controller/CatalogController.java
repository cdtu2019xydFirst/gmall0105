package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

//@CrossOrigin跨域问题,前端服务器访问后端服务器 ，实现前后端分离
@Controller
@CrossOrigin
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("GetCatlog1")
    @ResponseBody
    public List<PmsBaseCatalog1> GetCatalog1(){
        List<PmsBaseCatalog1> Catalog1s = catalogService.GetCatalog1();
        return Catalog1s;
    }

    @RequestMapping("GetCatlog2")
    @ResponseBody
    public List<PmsBaseCatalog2> GetCatalog2(String Catalog1Id){
        List<PmsBaseCatalog2> Catalog2s = catalogService.GetCatalog2(Catalog1Id);
        return Catalog2s;
    }

    @RequestMapping("GetCatlog3")
    @ResponseBody
    public List<PmsBaseCatalog3> GetCatalog3(String Catalog2Id){
        List<PmsBaseCatalog3> Catalog3s = catalogService.GetCatalog3(Catalog2Id);
        return Catalog3s;
    }

}
