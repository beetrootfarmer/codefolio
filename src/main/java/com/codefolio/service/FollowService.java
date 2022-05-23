package com.codefolio.service;

import com.codefolio.dto.user.response.GetFollowList;
import com.codefolio.vo.FollowVO;

import java.util.List;

public interface FollowService {

    int followUser(FollowVO follow);

    void UnFollowUser(FollowVO followVO);

    List<GetFollowList> getFollowUserList(String userUUID);

    int checkFollow(FollowVO followVO);
}
