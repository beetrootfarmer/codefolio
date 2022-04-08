package com.codefolio.service;

import org.springframework.transaction.annotation.Transactional;

import com.codefolio.vo.LikeVO;

public interface LikeService {
    
    @Transactional
    public void saveHeart(LikeVO lvo);
    
    @Transactional
    public void plusHeart(int projSeq);
    
    @Transactional
    public void deleteHeart(LikeVO lvo);
    
    @Transactional
    public void minusHeart(int projSeq);

	public int didUserLike(LikeVO lvo);
}