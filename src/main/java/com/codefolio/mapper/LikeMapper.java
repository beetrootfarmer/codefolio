package com.codefolio.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.codefolio.vo.LikeVO;

import java.util.Map;


// @Mapper Autowired에 연결하기 위한 어노테이션
@Mapper
public interface LikeMapper{
	
	public void saveHeart(LikeVO lvo);

	public void plusHeart(int projSeq);

	public void deleteHeart(LikeVO lvo);

	public void minusHeart(@Param("projSeq") int projSeq);

	public int didUserLike(LikeVO lvo);
}