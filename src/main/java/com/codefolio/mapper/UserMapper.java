package com.codefolio.mapper;

import java.util.List;

import com.codefolio.vo.UserVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    List<UserVO> getAllUserData();
}