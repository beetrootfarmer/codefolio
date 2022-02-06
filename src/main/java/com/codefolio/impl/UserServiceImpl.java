package com.codefolio.impl;

import com.codefolio.mapper.UserMapper;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }
}
