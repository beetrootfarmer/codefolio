package com.codefolio.service;

import com.codefolio.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface UserService {

    public UserVO getUser(String userId);

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

    //    public UserVO findUserName(String name);


//    public Map<String, Object> findLoginPwd(UserVO user);

//    @Transactional
//    public Map<String, Object> checkUserEmailDup(String userId);

}