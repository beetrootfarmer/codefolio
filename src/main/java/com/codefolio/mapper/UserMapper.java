package com.codefolio.mapper;


import com.codefolio.vo.UserVO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //DAO의 구현체를 마이바티스가 대신 구현해줌, 데이터 관련 모두 Mapper에게 위임
public interface UserMapper {

    public List<UserVO> getAllUserData();

    public UserVO getUser(int userSeq);

    public int joinUser(UserVO user);

    public void updateUser(UserVO user);

    public void delete(int userSeq);

    public int checkEmail(UserVO userEmail);

    public String checkLogin(UserVO user);


}