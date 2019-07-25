package com.atguigu.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    //不能用@Autowired 应为不在同一个工程    dubbo协议
    @Reference
    UserService userService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return"hello SpringBoot Dubbo";
    }

    //UmsMember增删改查
    //查询
    @RequestMapping("getUmsMemberById")
    @ResponseBody
    public List<UmsMember> getUmsMemberById(String Id){
        List<UmsMember> UmsMember = userService.getUmsMemberById(Id);
        return UmsMember;
    }

    //添加
    @RequestMapping("addUmsMember")
    @ResponseBody
    public String addUmsMember(UmsMember umsMember){
        userService.addUmsMember(umsMember);
        return "add Success" ;
    }

    //删除
    @RequestMapping("deletUmsMemberById")
    @ResponseBody
    public String deletUmsMemberById(String Id){
        userService.deletUmsMemberById(Id);
        return "delet success!";
    }

    //更新
    @RequestMapping("updataUmsMember")
    @ResponseBody
    public String updataUmsMember(UmsMember umsMember1){
        umsMember1.setUsername("钱三一");
        umsMember1.setPassword("987654321");
        umsMember1.setNickname("小钱");
        umsMember1.setCity("北京");
        userService.updataUmsMember(umsMember1);
        return "updata success!";
    }

    //UmsMemberReceiveAddress增删改查
    //查询
    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId){
        List<UmsMemberReceiveAddress> UmsMemberReceiveAddress = userService.getReceiveAddressByMemberId(memberId);
        return UmsMemberReceiveAddress;
    }

    //添加
    @RequestMapping("addUmsMemberReceiveAddress")//输入名字name
    @ResponseBody
    public String addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress){
        umsMemberReceiveAddress.setMemberId("1");
        umsMemberReceiveAddress.setPhoneNumber("15984787227");
        umsMemberReceiveAddress.setDefaultStatus(0);
        umsMemberReceiveAddress.setProvince("四川省");
        umsMemberReceiveAddress.setCity("成都市");
        umsMemberReceiveAddress.setRegion("郫都区");
        umsMemberReceiveAddress.setDetailAddress("信息工业园");
        userService.addUmsMemberReceiveAddress(umsMemberReceiveAddress);
        return "add address success!";
    }

    //删除
    @RequestMapping("deletUmsMemberReceiveAddressById")
    @ResponseBody
    public String deletUmsMemberReceiveAddressById(String Id){
        userService.deletUmsMemberReceiveAddressById(Id);
        return "delet address success!";
    }

    //修改
    @RequestMapping("updataUmsMemberReceiveAddress")
    @ResponseBody
    public String updataUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress1){
        umsMemberReceiveAddress1.setMemberId("1");
        umsMemberReceiveAddress1.setName("钱三一");
        umsMemberReceiveAddress1.setPhoneNumber("15984787229");
        umsMemberReceiveAddress1.setDefaultStatus(1);
        umsMemberReceiveAddress1.setProvince("四川省");
        umsMemberReceiveAddress1.setCity("成都市");
        umsMemberReceiveAddress1.setRegion("青羊区");
        umsMemberReceiveAddress1.setDetailAddress("开发区");
        userService.updataUmsMemberReceiveAddress(umsMemberReceiveAddress1);
        return "uptata address success!";
    }

}
