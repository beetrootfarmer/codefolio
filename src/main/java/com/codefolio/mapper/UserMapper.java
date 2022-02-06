package com.codefolio.mapper;

import java.util.List;
import com.codefolio.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public List<UserVO> getAllUserData();
}