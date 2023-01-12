package com.codefolio.controller;

import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.proj.response.GetProjandFileResponse;
import com.codefolio.dto.proj.response.ProjListResponse;
import com.codefolio.config.exception.controller.NotFoundException;
import com.codefolio.config.exception.controller.GlobalException;
import com.codefolio.service.FileService;
import com.codefolio.service.ProjService;
import com.codefolio.utils.FileUtils;
import com.codefolio.vo.Criteria;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.ProjVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/proj")
public class ProjController {
	@Autowired
    ProjService projService;
	
    @Autowired
    FileService fileService;
	
    @Autowired
    FileUtils fileUtils;

    @Autowired
	private JwtTokenProvider jwtTokenProvider;




	@GetMapping("/search")
	   public ResponseEntity<?> projList(@RequestParam(required = false) String keyword, ProjVO vo,Criteria cri) {
			log.info("[API] projList");
	       
	       if(vo.getKeywordType() == null) {
	           vo.setKeywordType("TITLE");
	       }
	       if(vo.getKeyword() == null) {
	           vo.setKeyword("");
	       }
	       keyword = vo.getKeyword();
	      
	       if(!keyword.equals(vo.getKeyword())) {
	           cri.setPageNum(1);
	       }
	       List<HashMap<String, Object>> projList = projService.getProjList(keyword,cri);
	
	        ProjListResponse data = new ProjListResponse(projList);
	        return ResponseEntity.ok(new JsonResponse(data,"success", 200, "projList"));
	   }



	// [프로젝트 상세 페이지] +[파일 불러오기]
	@GetMapping("/{projSeq}")
	public ResponseEntity<?> getProjDetail(@PathVariable("projSeq") int seq) {
		log.info("[API] getProjDetail");
	   // 보드 시퀀스로 파일리스트 가져오기
	   List<FileVO> fileList = fileService.getFileListBySeq(seq);
	
	   // 조회수 늘리기
	   projService.viewUp(seq);
	   ProjVO proj = projService.getProjDetail(seq);
	
	   for (FileVO file : fileList) {
	           if (file.getBoardSeq() == seq) {
	        	   GetProjandFileResponse data = new GetProjandFileResponse(proj, fileList);
	    	       return ResponseEntity.ok(new JsonResponse(data,"success", 200, "getProjDetail"));
	           }
	       }
	       GetProjandFileResponse data = new GetProjandFileResponse(proj, fileList);
	       return ResponseEntity.ok(new JsonResponse(data,"success", 200, "getProjDetail"));
	}

	
  // [프로젝트 추가] + [파일 추가] ~~~ 썸네일 RequesetParam으로 받아오기  
  @PostMapping("/add")
  public ResponseEntity<?> addProj(@RequestParam(required = false) MultipartFile tn, @RequestParam(required = false) MultipartFile[] file,ProjVO vo) throws IOException  {
	  		log.info("[API] addProj");
	  		try{
 	 			if(!tn.isEmpty()) {
     	 		String thumbnail = projService.makeThumbnail(tn);
     	 		vo.setThumbnail(thumbnail);
 	 		}
 	 		}catch(NullPointerException e){
 	 			log.error("[NullPointerException] addProj");
 	 		}
             int projSeq = projService.getProjSeq();

             List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj",file);

             if(CollectionUtils.isEmpty(fileList) == false) {
                 fileService.saveFile(fileList);
             }
             	
            projService.addProj(vo);
            ProjVO projDetail = projService.getProjDetail(projSeq);
            
            GetProjandFileResponse data = new GetProjandFileResponse(projDetail, fileList);
 	       return ResponseEntity.ok(new JsonResponse(data,"success", 200, "addProj"));
 }



// [프로젝트 삭제]
        @DeleteMapping("/{projSeq}")
        public ResponseEntity<?> deleteProj(@PathVariable("projSeq") int projSeq) {
        	log.info("[API] deleteProj");
        	
            projService.deleteProj(projSeq);
            ProjVO projDetail = projService.getProjDetail(projSeq);
            
            ProjListResponse data = new ProjListResponse(projDetail);
	        return ResponseEntity.ok(new JsonResponse(data,"success", 200, "deleteProj"));
            }



// [프로젝트 수정]  + [파일 수정]
		@PutMapping("update/{projSeq}")
		public ResponseEntity<?> updateProj(@PathVariable("projSeq") int projSeq,@RequestParam(required = false) MultipartFile tn, 
											@RequestParam(required = false) MultipartFile[] file,
		                                    ProjVO vo) {
			log.info("[API] updateProj");
			try {
			
				if(!tn.isEmpty()) {
	    	 		String thumbnail = projService.makeThumbnail(tn);
	    	 		vo.setThumbnail(thumbnail);
		 		}
			
			    // 보드 시퀀스로 파일 삭제 후 새로 받아온 파일 넣어주기 
			    fileService.deleteFileBySeq(projSeq);
			    
			    int fileSeq = fileService.getFileSeq();
			    FileUtils fileUtils = new FileUtils();
			    List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj", file);
			
			    if(CollectionUtils.isEmpty(fileList) == false) {
			        fileService.saveFile(fileList);
			        }
			
			
			    projService.update(vo);
			    ProjVO projDetail = projService.getProjDetail(projSeq);
			    
			    GetProjandFileResponse data = new GetProjandFileResponse(projDetail, fileList);
	 	       	return ResponseEntity.ok(new JsonResponse(data, "success",200,"updateProj"));
			} catch(IOException e){
				log.error("[IOException] updateProj");
				return ResponseEntity.badRequest().body("[IOException] updateProj");
				
			}
		    
		}
		
// 유저별 좋아요한 프로젝트 조회
		@GetMapping("likeProj/{userId}")
		public ResponseEntity<?> getLikeProj(@PathVariable("userId") String userId, ProjVO vo,Criteria cri) {
		log.info("[API] getLikeProj");
		
		cri.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getLikeProj(userId,cri);
		ProjListResponse data = new ProjListResponse(projList);
        return ResponseEntity.ok(new JsonResponse(data, "success",200, "likeProj"));
		}
		
// 좋아요 순 프로젝트 리스트
		@GetMapping("/bestProj") 
		public ResponseEntity<?> getbestProj(ProjVO vo,Criteria criteria) {
		log.info("[API] getbestProj");
		
		criteria.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getBestProj(criteria);
		ProjListResponse data = new ProjListResponse(projList);
        return ResponseEntity.ok(new JsonResponse(data,"success", 200, "bestProj"));
		}

}
