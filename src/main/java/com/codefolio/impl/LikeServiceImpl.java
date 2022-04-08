package com.codefolio.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codefolio.mapper.LikeMapper;
import com.codefolio.service.LikeService;
import com.codefolio.vo.LikeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeMapper likeMapper;

	@Override
	public void saveHeart(LikeVO lvo){
		likeMapper.saveHeart(lvo);
	}

	@Override
	public void plusHeart(int projSeq){
		likeMapper.plusHeart(projSeq);
	}

	@Override
	public void deleteHeart(LikeVO lvo){
		likeMapper.deleteHeart(lvo);
	}

	@Override
	public void minusHeart(int projSeq){
		likeMapper.minusHeart(projSeq);
	}

	@Override
	public int didUserLike(LikeVO lvo) {
		return likeMapper.didUserLike(lvo);
	}
    
	


}