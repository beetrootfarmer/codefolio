package com.codefolio.controller;

import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.proj.response.GetProjandFileResponse;
import com.codefolio.dto.proj.response.ProjListResponse;
import com.codefolio.config.exception.controller.NotFoundException;
import com.codefolio.config.exception.controller.BadRequestException;
import com.codefolio.config.exception.controller.GlobalException;
import com.codefolio.service.FileService;
import com.codefolio.service.ProjService;
import com.codefolio.service.UserService;
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
@RequestMapping("/auth")
public class ProjSecurityController {
	@Autowired
    ProjService projService;
	
    @Autowired
    FileService fileService;
	
    @Autowired
    FileUtils fileUtils;

    @Autowired
	private  JwtTokenProvider jwtTokenProvider;

    @Autowired
	private  UserService userService;



	
  // [프로젝트 추가] + [파일 추가] ~~~ 썸네일 RequesetParam으로 받아오기  
  @PostMapping("/{userId}/add")
  public ResponseEntity<?> addProj(@PathVariable String userId,
  									@RequestParam(required = false) MultipartFile tn, 
  									@RequestParam(required = false) MultipartFile[] file,
									ProjVO vo, 
									HttpServletRequest request) 
									throws IOException  {
	  		log.info("[API] addProj");

			String userUUID = getUUID(request);
			String getuserUUID = userService.getUUIDById(userId);
			if(!getuserUUID.equals(userUUID)) throw new BadRequestException("user url error");

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
		@PutMapping("/{userId}/update/{projSeq}")
		public ResponseEntity<?> updateProj(@PathVariable String userId,
											@PathVariable("projSeq") int projSeq,
											@RequestParam(required = false) MultipartFile tn, 
											@RequestParam(required = false) MultipartFile[] file, 
											HttpServletRequest request,
		                                    ProjVO vo) {
			log.info("[API] updateProj");

			String userUUID = getUUID(request);
			String getuserUUID = userService.getUUIDById(userId);
			if(!getuserUUID.equals(userUUID)) throw new BadRequestException("user url error");

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
		public ResponseEntity<?> getLikeProj(@PathVariable String userId, ProjVO vo,Criteria cri, HttpServletRequest request) {		
		log.info("[API] getLikeProj");

		String userUUID = getUUID(request);
        String getuserUUID = userService.getUUIDById(userId);
        if(!getuserUUID.equals(userUUID)) throw new BadRequestException("user url error");
		
		cri.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getLikeProj(userId,cri);
		ProjListResponse data = new ProjListResponse(projList);
        return ResponseEntity.ok(new JsonResponse(data, "success",200, "likeProj"));
		}
		
		private String getUUID(HttpServletRequest request){
			try{
				String getAcToken = jwtTokenProvider.resolveToken(request);
				return jwtTokenProvider.getUserPk(getAcToken);
			}catch (NullPointerException e){
				throw new NotFoundException("not Resolve AcToken");
	
			} catch (Exception e){
				throw new GlobalException("global Exception");
			}
		}

}
