package com.codefolio.impl;


import com.codefolio.mapper.UserMapper;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    //JoinUser을 하는 동안에 auto_increment에 의해 생긴 주키 값을 알아내야 함
    @Override
    public int joinUser(UserVO user) {
        return userMapper.joinUser(user);
//        log.info("newId : "+user.getUserSeq());
//        return CUtil.getAsLong(user.getUserSeq());
    }

    @Override
    public UserVO getUser(int userSeq){return userMapper.getUser(userSeq);}

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }

    @Override
    public void updateUser(UserVO user){ userMapper.updateUser(user);}

    @Override
    public void delete(int userSeq){
        userMapper.delete(userSeq);
    }

    //TODO: checkEmail null 오류
    @Override
    public int checkEmail(UserVO userEmail){
        return userMapper.checkEmail(userEmail);
    }

    @Override
    public String checkLogin(UserVO user){return userMapper.checkLogin(user);}


}
