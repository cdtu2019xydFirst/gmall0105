package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
//@CrossOrigin跨域问题,前端服务器访问后端服务器 ，实现前后端分离
@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    //attrInfo
    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String Catalog3Id){
        List<PmsBaseAttrInfo> PmsBaseAttrInfo = attrService.attrInfoList(Catalog3Id);
        return PmsBaseAttrInfo;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo){
        attrService.saveAttrInfo(pmsBaseAttrInfo);
            return "save attr info success!";
    }


    //attrValue
    @RequestMapping("GetattrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> GetattrValueList(String attrId){
        List<PmsBaseAttrValue> PmsBaseAttrValue = attrService.attrValueList(attrId);
        return PmsBaseAttrValue;
    }

}
