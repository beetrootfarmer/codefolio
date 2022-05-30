package com.codefolio.mapper;

import com.codefolio.dto.user.response.GetFollowList;
import com.codefolio.vo.FollowVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    int followUser(FollowVO follow);

    void UnFollowUser(FollowVO followVO);

    List<GetFollowList> getFollowUserList(String userUUID);

    int checkFollow(FollowVO followVO);
}
