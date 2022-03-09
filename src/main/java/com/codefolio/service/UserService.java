package com.codefolio.service;

import com.codefolio.vo.UserVO;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public interface UserService {

    public UserVO getUser(String userId);

    public List<UserVO> getAllUserData();

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void deleteUser(String userId);

    public int checkEmail(String userEmail);


    public String checkLogin(UserVO user);

    public int checkName(String userName);

    public UserVO getUserObj(Object param);

    public UserVO getUserById(String id);

//     시큐리티 로그인
    public String secLogin(UserVO user);
}