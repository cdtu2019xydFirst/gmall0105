package com.atguigu.gmall.user.service.impl;

import com.atguigu.gmall.user.bean.UmsMember;
import com.atguigu.gmall.user.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.atguigu.gmall.user.mapper.UserMapper;
import com.atguigu.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> memberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        return memberReceiveAddresses;
    }

    @Override
    public List<UmsMember> getUmsMemberById(String id) {
        UmsMember umsMember = new UmsMember();
        umsMember.setId(id);
        List<UmsMember> umsMembers = userMapper.select(umsMember);
        return umsMembers;
    }

    @Override
    public void addUmsMember(UmsMember umsMember) {
        userMapper.insert(umsMember);
    }

    @Override
    public void deletUmsMemberById(String Id) {
        userMapper.deleteByPrimaryKey(Id);
    }

    @Override
    public void updataUmsMember(UmsMember umsMember) {
        userMapper.updateByPrimaryKey(umsMember);
    }

    @Override
    public void addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress) {
        umsMemberReceiveAddressMapper.insert(umsMemberReceiveAddress);
    }

    @Override
    public void deletUmsMemberReceiveAddressById(String Id) {
        umsMemberReceiveAddressMapper.deleteByPrimaryKey(Id);
    }

    @Override
    public void updataUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress1) {
        umsMemberReceiveAddressMapper.updateByPrimaryKey(umsMemberReceiveAddress1);
    }


}
