package com.codefolio.service;

import com.codefolio.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface UserService {

    public List<UserVO> getAllUserData();

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void deleteUser(String userId);

    public int checkEmail(String userEmail);


    public String checkLogin(UserVO user);

    public int checkId(String userId);

    public UserVO getUserById(String id);

    void updateUserImg(UserVO user);

    String secLogin(UserVO user);

    void updateRefToken(UserVO userVO);

    public UserVO getUserByEmail(String email);

    public String getSecString();

    public String getSaltKey(String email);
}