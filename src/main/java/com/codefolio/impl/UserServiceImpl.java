package com.codefolio.impl;

import com.codefolio.mapper.UserMapper;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }
}