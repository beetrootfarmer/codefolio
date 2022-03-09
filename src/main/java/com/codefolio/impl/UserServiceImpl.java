package com.codefolio.impl;


import com.codefolio.mapper.UserMapper;
import com.codefolio.service.MailService;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MailService mailService;

    @Autowired
    BCryptPasswordEncoder encoder;



    //JoinUser을 하는 동안에 auto_increment에 의해 생긴 주키 값을 알아내야 함
    @Override
    public int joinUser(UserVO user) {return userMapper.joinUser(user);}

    @Override
    public UserVO getUser(String userId){
        return userMapper.getUser(userId);
    }

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }

    @Override
    public void updateUser(UserVO user){ userMapper.updateUser(user);}

    @Override
    public void deleteUser(String userId){
        userMapper.deleteUser(userId);
    }

    @Override
    public int checkEmail(String userEmail){
        return userMapper.checkEmail(userEmail);
    }
    @Override
    public int checkName(String userName){return userMapper.checkName(userName);}

    @Override
    public int checkId(String userId){return userMapper.checkId(userId);}

    @Override
    public UserVO getUserById(String id){return userMapper.getUserById(id);}

    @Override
    public String secLogin(UserVO user){return userMapper.secLogin(user);}


    @Override
    public String checkLogin(UserVO user){return userMapper.checkLogin(user);}

    @Override
    public UserVO getUserObj(Object param) {
        return userMapper.getUserObj(param);
    }

    @Override
    public UserVO getUserById(String id) {
        return userMapper.getUserById(id);
    }

    //     시큐리티 로그인
    @Override
    public String secLogin(UserVO user){
        return userMapper.secLogin(user);
    };

    @Override
    public void updateUserImg(UserVO user){userMapper.updateUserImg(user);};

}
