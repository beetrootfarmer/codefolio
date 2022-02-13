package com.codefolio.service;

import com.codefolio.vo.UserVO;

import java.util.List;
import java.util.Map;


public interface UserService {

    public UserVO getUser(int userSeq);

    public List<UserVO> getAllUserData();

    public void joinUser(Map<String, Object> param);

    public void updateUser(Map<String, Object> param);

    public void delete(int userSeq);

    //    public Map<String, Object> checkUserIdDup(String userId);

}