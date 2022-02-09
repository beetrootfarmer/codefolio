package com.codefolio.service;

import com.codefolio.vo.UserVO;

import java.util.List;
import java.util.Map;


public interface UserService {
    public List<UserVO> getAllUserData();

    public UserVO getUser();

//    public Map<String, Object> checkUserIdDup(String userId);

    public void JoinUser(Map<String, Object> param);
}