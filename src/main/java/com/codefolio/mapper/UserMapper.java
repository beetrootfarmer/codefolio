package com.codefolio.mapper;


import com.codefolio.vo.FollowVO;
import com.codefolio.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //DAO의 구현체를 마이바티스가 대신 구현해줌, 데이터 관련 모두 Mapper에게 위임
public interface UserMapper {

    public List<UserVO> getAllUserData();

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void deleteUser(String userId);

    public int checkEmail(String userEmail);

    public String checkLogin(UserVO user);

    int checkId(String userId);

    void updateUserImg(UserVO user);

    public UserVO getUserById(String id);

    public String secLogin(UserVO user);


    public UserVO getUserByEmail(String email);

    String getSaltKey(String email);

    void updateUserId(String userUUID, String getUserId);

    String getUUIDById(String userId);

    UserVO getUserByUUID(String userUUID);

    void updatePwd(UserVO getUser);

    int followUser(FollowVO follow);
}