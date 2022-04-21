package com.codefolio.mapper;

import com.codefolio.vo.FollowVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
    int followUser(FollowVO follow);
}
