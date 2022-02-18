package com.codefolio.service;

import com.codefolio.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserService {

    @Transactional
    public UserVO getUser(int userSeq);

    @Transactional(readOnly = true)
    public List<UserVO> getAllUserData();

    @Transactional
    public int joinUser(UserVO user);

    @Transactional
    public void updateUser(UserVO user);

    @Transactional
    public void delete(int userSeq);

    @Transactional
    public int checkEmail(UserVO userEmail);

    @Transactional
    public String checkLogin(UserVO user);

//    @Transactional
//    public Map<String, Object> checkUserEmailDup(String userId);

}