package com.atguigu.gmall.user.service;

import com.atguigu.gmall.user.bean.JsonResult;
import com.atguigu.gmall.user.bean.UmsMember;
import com.atguigu.gmall.user.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    List<UmsMember> getUmsMemberById(String id);

    void addUmsMember(UmsMember umsMember);

    void deletUmsMemberById(String Id);

    void updataUmsMember(UmsMember umsMember);

    void addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress);

    void deletUmsMemberReceiveAddressById(String Id);

    void updataUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress1);

}
