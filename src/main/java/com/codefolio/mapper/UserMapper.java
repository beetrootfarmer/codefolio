package com.codefolio.mapper;


import com.codefolio.vo.UserVO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //DAO의 구현체를 마이바티스가 대신 구현해줌, 데이터 관련 모두 Mapper에게 위임
public interface UserMapper {

    public List<UserVO> getAllUserData();

    public UserVO getUser(String userName);

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void delete(String userName);

    public int checkEmail(String userEmail);

    public String checkLogin(UserVO user);

    UserVO searchEmail(String email);

    int checkName(String userName);
}