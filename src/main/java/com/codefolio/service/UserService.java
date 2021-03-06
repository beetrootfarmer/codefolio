package com.codefolio.service;

import com.codefolio.vo.FollowVO;
import com.codefolio.vo.UserVO;

import java.util.List;


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


    public UserVO getUserByEmail(String email);

    public String getSecString();

    public String getSaltKey(String email);

    void updateUserId(String userUUID, String getUserId);

    String getUUIDById(String userId);

    UserVO getUserByUUID(String userUUID);

    void updatePwd(UserVO getUser);

    int followUser(FollowVO follow);


}