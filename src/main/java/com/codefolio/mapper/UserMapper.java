package com.codefolio.mapper;


import com.codefolio.vo.UserVO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper //DAO의 구현체를 마이바티스가 대신 구현해줌, 데이터 관련 모두 Mapper에게 위임
public interface UserMapper {
    public List<UserVO> getAllUserData();

    public UserVO getUser(int userSeq);

    public void joinUser(Map<String, Object> param);

    public void updateUser(Map<String, Object> param);

    public void delete(int userSeq);

    //    public int getuserIdDupCount(String userId);

}