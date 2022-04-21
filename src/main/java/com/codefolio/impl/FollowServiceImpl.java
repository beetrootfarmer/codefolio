package com.codefolio.impl;

import com.codefolio.mapper.FollowMapper;
import com.codefolio.service.FollowService;
import com.codefolio.vo.FollowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public int followUser(FollowVO follow){return followMapper.followUser(follow);}
}
