package com.codefolio.service;

import com.codefolio.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserService {

    public UserVO getUser(String userName);

    public List<UserVO> getAllUserData();

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void delete(String userName);

    public int checkEmail(String userEmail);


    public String checkLogin(UserVO user);

    public int checkName(String userName);


}