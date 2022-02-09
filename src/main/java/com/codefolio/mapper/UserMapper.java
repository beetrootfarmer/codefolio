package com.codefolio.mapper;


import com.codefolio.vo.UserVO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper //DAO의 구현체를 마이바티스가 대신 구현해줌, 데이터 관련 모두 Mapper에게 위임
public interface UserMapper {
    public List<UserVO> getAllUserData();

    UserVO getUser();

//    public int getuserIdDupCount(String userId);

    public void JoinUser(Map<String, Object> param);
}