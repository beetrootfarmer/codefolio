package com.codefolio.impl;


import com.codefolio.mapper.UserMapper;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void JoinUser(Map<String, Object> param) {
        userMapper.JoinUser(param);
    }

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }

    @Override
    public UserVO getUser(){return userMapper.getUser();}

//
//    @Override
//    public Map<String, Object> checkUserIdDup(String userId){
//        int count = userMapper.getuserIdDupCount(userId);
//
//
//        String resultCode="";
//        String msg="";
//
//        if(count==0){
//            resultCode="S-1";
//            msg="사용가능한 로그인 ID 입니다.";
//        }
//        else{
//            resultCode="F-1";
//            msg="이미 사용중인 로그인 ID 입니다.";
//        }
//
//        Map<String, Object> rs = new HashMap<String, Object>();
//        rs.put("resultCode",resultCode);
//        rs.put("msg",msg);
//
//        return rs;
//    }




}
