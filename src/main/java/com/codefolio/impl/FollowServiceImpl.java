package com.codefolio.impl;

import com.codefolio.dto.user.response.GetFollowList;
import com.codefolio.mapper.FollowMapper;
import com.codefolio.service.FollowService;
import com.codefolio.vo.FollowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public int followUser(FollowVO follow){return followMapper.followUser(follow);}

    @Override
    public void UnFollowUser(FollowVO followVO){followMapper.UnFollowUser(followVO);}

    @Override
    public int checkFollow(FollowVO followVO){return followMapper.checkFollow(followVO);}

    @Override
    public List<GetFollowList> getFollowUserList(String userUUID){return followMapper.getFollowUserList(userUUID);}
}
