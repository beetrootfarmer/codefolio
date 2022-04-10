package com.codefolio.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codefolio.config.exception.NotCreateException;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.response.GetProjandFileResponse;
import com.codefolio.dto.response.GetResponse;
import com.codefolio.dto.response.ProjListResponse;
import com.codefolio.service.FileService;
import com.codefolio.service.ProjService;
import com.codefolio.vo.Criteria;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.ProjVO;

import com.codefolio.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import com.codefolio.utils.FileUtils;

import org.springframework.util.CollectionUtils;

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




	@GetMapping("/search")
	   public ResponseEntity<?> searchProjList(@RequestParam(required = false) String keyword, ProjVO vo,Criteria cri) {
//		ResponseEntity<List<HashMap<String, Object>>>
			log.info("프로젝트 목록 검색 api");
	       
	       if(vo.getKeywordType() == null) {
	           vo.setKeywordType("TITLE");
	       }
	       if(vo.getKeyword() == null) {
	           vo.setKeyword("");
	       }
	       int total = projService.selectProjCount(vo);
	
	       keyword = vo.getKeyword();
	       log.info("keyword",keyword,"_______________");
	       
	       if(!keyword.equals(vo.getKeyword())) {
	           log.info("!keyword.equals");
	           cri.setPageNum(1);
	           
	       }
	       //List<HashMap<String, Object>> projList = projService.searchProj(keyword);
	       List<HashMap<String, Object>> projList = projService.getProjList(keyword,cri);
	
	        ProjListResponse data = new ProjListResponse(projList);
	        return ResponseEntity.ok(new JsonResponse(data, 200, "projList"));
	   }



	// [프로젝트 상세 페이지] +[파일 불러오기]
	@GetMapping("/{projSeq}")
	public ResponseEntity<?> showProjDetail(@PathVariable("projSeq") int seq) {
	   // 보드 시퀀스로 파일리스트 가져오기
	   List<FileVO> fileList = fileService.getFileListBySeq(seq);
	
	   // 조회수 늘리기
	   projService.viewUp(seq);
	   
	   ProjVO proj = projService.getProjDetail(seq);
	
	   for (FileVO file : fileList) {
	           if (file.getBoardSeq() == seq) {
	        	   GetProjandFileResponse data = new GetProjandFileResponse(proj, fileList);
	    	       return ResponseEntity.ok(new JsonResponse(data, 200, "projDetail"));
	           }
	       }
	
	       GetProjandFileResponse data = new GetProjandFileResponse(proj, fileList);
	       return ResponseEntity.ok(new JsonResponse(data, 200, "projDetail"));
	}


//      // [프로젝트 추가] + [파일 추가] ~~~ 썸네일 RequesetParam으로 받아오기  
//         @PostMapping("/add")
//         public ResponseEntity<?> insertProjandThumbnail(@RequestParam(required = false) MultipartFile tn, ProjVO vo, HttpServletRequest request,
//                 MultipartHttpServletRequest mhsr) throws IOException  {
//        	 		try{
//        	 			if(!tn.isEmpty()) {
//	        	 		String thumbnail = projService.makeThumbnail(tn);
//	        	 		vo.setThumbnail(thumbnail);
//        	 		}
//        	 		}catch(NullPointerException e){
//        	 			tn = null;
//        	 		}
//                    int projSeq = projService.getProjSeq();
//
//                    FileUtils fileUtils = new FileUtils();
//
//                    List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj", request, mhsr);
//
//                    if(CollectionUtils.isEmpty(fileList) == false) {
//                        fileService.saveFile(fileList);
//                    }
//                    	
//                   projService.addProj(vo);
//                   ProjVO projDetail = projService.getProjDetail(projSeq);
//                   
//                   GetProjandFileResponse data = new GetProjandFileResponse(projDetail, fileList);
//        	       return ResponseEntity.ok(new JsonResponse(data, 200, "addProj"));
//        }
	
  // [프로젝트 추가] + [파일 추가] ~~~ 썸네일 RequesetParam으로 받아오기  
  @PostMapping("/add")
  public ResponseEntity<?> insertProjandThumbnail(@RequestParam(required = false) MultipartFile tn, @RequestParam(required = false) MultipartFile[] file,ProjVO vo) throws IOException  {
 	 		try{
 	 			if(!tn.isEmpty()) {
     	 		String thumbnail = projService.makeThumbnail(tn);
     	 		vo.setThumbnail(thumbnail);
 	 		}
 	 		}catch(NullPointerException e){
 	 			tn = null;
 	 		}
             int projSeq = projService.getProjSeq();

//             FileUtils fileUtils = new FileUtils();

             List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj",file);

             if(CollectionUtils.isEmpty(fileList) == false) {
                 fileService.saveFile(fileList);
             }
             	
            projService.addProj(vo);
            ProjVO projDetail = projService.getProjDetail(projSeq);
            
            GetProjandFileResponse data = new GetProjandFileResponse(projDetail, fileList);
 	       return ResponseEntity.ok(new JsonResponse(data, 200, "addProj"));
 }


// [프로젝트 삭제]
        @DeleteMapping("/{projSeq}")
        public ResponseEntity<?> deleteProj(@PathVariable("projSeq") int projSeq) {
            projService.deleteProj(projSeq);
            ProjVO projDetail = projService.getProjDetail(projSeq);
            
            ProjListResponse data = new ProjListResponse(projDetail);
	        return ResponseEntity.ok(new JsonResponse(data, 200, "deleteProj"));
            }



// [프로젝트 수정]  + [파일 수정]
//		@PutMapping("update/{projSeq}")
//		public ResponseEntity<?> showUpdate(@PathVariable("projSeq") int projSeq,@RequestParam("tn") MultipartFile tn,
//		                                    ProjVO vo,HttpServletRequest request,
//		                                    MultipartHttpServletRequest mhsr) throws IOException {
//			
//			if(!tn.isEmpty()) {
//    	 		String thumbnail = projService.makeThumbnail(tn);
//    	 		vo.setThumbnail(thumbnail);
//	 		}
//		
//		    // 보드 시퀀스로 파일 삭제 후 새로 받아온 파일 넣어주기 
//		    fileService.deleteFileBySeq(projSeq);
//		    
//		    int fileSeq = fileService.getFileSeq();
//		    FileUtils fileUtils = new FileUtils();
//		    List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj", request, mhsr);
//		
//		    if(CollectionUtils.isEmpty(fileList) == false) {
//		        fileService.saveFile(fileList);
//		        }
//		
//		
//		    projService.update(vo);
//		    ProjVO projDetail = projService.getProjDetail(projSeq);
//		    
//		    GetProjandFileResponse data = new GetProjandFileResponse(projDetail, fileList);
// 	       	return ResponseEntity.ok(new JsonResponse(data, 200, "updateProj"));
//		    
//		}
// 유저별 좋아요한 프로젝트 조회
		@GetMapping("likeProj/{userId}") //proj/userId
		public ResponseEntity<?> getLikeProj(@PathVariable("userId") String userId, ProjVO vo,Criteria cri) {
//			List<HashMap<String, Object>>
		log.info("유저가 좋아요 한 프로젝트 목록 api");
		
		cri.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getLikeProj(userId,cri);
		ProjListResponse data = new ProjListResponse(projList);
        return ResponseEntity.ok(new JsonResponse(data, 200, "likeProj"));
		}
		
// 좋아요 순 프로젝트 리스트
		@GetMapping("/bestProj") 
		public ResponseEntity<?> getbestProj(ProjVO vo,Criteria criteria) {
//			List<HashMap<String, Object>>
		log.info("좋아요 순 프로젝트 목록 api");
		
		criteria.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getBestProj(criteria);
		ProjListResponse data = new ProjListResponse(projList);
        return ResponseEntity.ok(new JsonResponse(data, 200, "bestProj"));
		}
}
