package com.codefolio.service;

import com.codefolio.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface UserService {

    public UserVO getUser(String userName);

    public List<UserVO> getAllUserData();

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void delete(String userName);

    public int checkEmail(String userEmail);


    public String checkLogin(UserVO user);

    public int checkName(String userName);


    public Map<String, Object> findLoginPwd(UserVO user);

//    @Transactional
//    public Map<String, Object> checkUserEmailDup(String userId);

}