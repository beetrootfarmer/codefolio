package com.codefolio.controller;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codefolio.service.LikeService;
import com.codefolio.vo.LikeVO;
import com.codefolio.vo.UserVO;

@Slf4j
@RestController
public class LikeController {

	@Autowired
	LikeService likeService;
	
	
	@PostMapping("like/{projSeq}")
	public ResponseEntity<?> likeProj(@PathVariable("projSeq") int projSeq, @RequestParam("userId") String userId) {
	
	if (userId != null) {	
		LikeVO lvo = new LikeVO();
		lvo.setProjSeq(projSeq);
		lvo.setUserId(userId);
		int userLiked = likeService.didUserLike(lvo);
		
		if (userLiked >0) {
			log.info("userLiked  >0");
			//	만약 유저가 해당 프로젝트의 좋아요를 누른 이력이 있다면 ??  
			//	좋아요 취소 기능이 된다 
			likeService.deleteHeart(lvo);
			likeService.minusHeart(projSeq);
			return ResponseEntity.ok(userId + "님이" + projSeq + "좋아요 취소");
		} else {			
			//	만약 유저가 해당 프로젝트의 좋아요를 누른 이력이 없다면? LIke 테이블에서 해당 프로젝트에 유저 아이디가 있는지 확인 
			likeService.saveHeart(lvo);
			// 프로젝트의 하트 개수 늘리기 
			likeService.plusHeart(projSeq);
			return ResponseEntity.ok(userId + "님이" + projSeq + "좋아요 !");
		}
	}else {
//		만약 유저 아이디가 NULL이면(로그인 상태가 아니라면) 로그인 요청 
	return ResponseEntity.ok("로그인 요청" + projSeq + "좋아요 할 수 없습니다.");
	}
}

}