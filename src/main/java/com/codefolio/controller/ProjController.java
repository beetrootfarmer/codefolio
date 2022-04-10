package com.codefolio.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codefolio.config.exception.NotCreateException;
import com.codefolio.dto.JsonResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("hello")
        public String hello(){
            return "helloTest";
        }



@GetMapping("/list")
public ResponseEntity<?> getProjList(ProjVO vo, Criteria cri) {
    log.info("프로젝트 목록 검색 api");
    
    if(vo.getKeywordType() == null) {
           vo.setKeywordType("TITLE");
       }
       if(vo.getKeyword() == null) {
           vo.setKeyword("");
       }
    int total = projService.selectProjCount(vo);
    List<ProjVO> projList = projService.getProjListOri();
    
    List<HashMap<String, Object>>  mainList = projService.getProjandFile();

    return ResponseEntity.ok(mainList);     
}

@GetMapping("/search")
   public ResponseEntity<List<HashMap<String, Object>>> searchProjList(@RequestParam("keyword") String keyword, ProjVO vo,Criteria cri) {
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

       log.info("cri,total",cri,total,"_______________");
       log.info("keywordType",vo.getKeywordType(),"_______________");
       return ResponseEntity.ok(projList);
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
               return ResponseEntity.ok(proj + "+ " + fileList );
           }
       }

       return ResponseEntity.ok(proj + "+ " + fileList );
}


// [프로젝트 추가] + [파일 추가] test
         @PostMapping("/add")
         public ResponseEntity<?> insertProjFile(ProjVO vo,HttpServletRequest request,
                        MultipartHttpServletRequest mhsr) throws IOException  {

                    int projSeq = projService.getProjSeq();

                    int fileSeq = fileService.getFileSeq();
                    FileUtils fileUtils = new FileUtils();

                    List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj", request, mhsr);

                    if(CollectionUtils.isEmpty(fileList) == false) {
                        fileService.saveFile(fileList);
                        System.out.println("saveFile()탐 + fileList===" + fileList);
                    }

                    projService.addProj(vo);
                   ProjVO projDetail = projService.getProjDetail(projSeq);
//  projSeq와 fileSeq가 return값에 담겨있지 않음.
                   return ResponseEntity.ok(projSeq+"번 프로젝트가 추가되었습니다"+ "projVO" + vo + "fileSeq="+fileSeq +fileList);
        }


// [프로젝트 삭제]
        @DeleteMapping("/{projSeq}")
        public ResponseEntity<String> deleteProj(@PathVariable("projSeq") int projSeq) {
            projService.deleteProj(projSeq);
            ProjVO projDetail = projService.getProjDetail(projSeq);
            return ResponseEntity.ok(projSeq+"번 프로젝트가 삭제되었습니다");
            }


//        //hweyoung update
//        @PutMapping("/preview/{projSeq}")
//        @ResponseBody
//        public ResponseEntity updatePreview(@PathVariable int projSeq,HttpServletRequest request, MultipartHttpServletRequest mhsr)throws Exception{
//            FileUtils fileUtils = new FileUtils();
//            String preview="";
//            List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"user", request,mhsr);
//            if(CollectionUtils.isEmpty(fileList) == false) {
//                fileService.saveFile(fileList);
//                preview = fileList.get(0).getFileDownloadUri();
//                projService.updatePreview(projSeq,preview);
//            }else throw new NotCreateException("Unable create file");
//            return ResponseEntity.ok(new JsonResponse(preview,200,"updatePreview"));
//        }



// [프로젝트 수정]  + [파일 수정]
		@PutMapping("update/{projSeq}")
		public ResponseEntity<?> showUpdate(@PathVariable("projSeq") int projSeq,
		                                    ProjVO vo,HttpServletRequest request,
		                                    MultipartHttpServletRequest mhsr) throws IOException {
		
		    // 보드 시퀀스로 파일 삭제 후 새로 받아온 파일 넣어주기 
		    fileService.deleteFileBySeq(projSeq);
		    
		    int fileSeq = fileService.getFileSeq();
		    FileUtils fileUtils = new FileUtils();
		    List<FileVO> fileList = fileUtils.parseFileInfo(projSeq,"proj", request, mhsr);
		
		    if(CollectionUtils.isEmpty(fileList) == false) {
		        fileService.saveFile(fileList);
		        System.out.println("saveFile()탐 + fileList===" + fileList);
		        }
		
		
		    projService.update(vo);
		    ProjVO projDetail = projService.getProjDetail(projSeq);
		    return ResponseEntity.ok(projSeq+"번 프로젝트 수정이 완료되었습니다" + projDetail + fileList);
		}
// 유저별 좋아요한 프로젝트 조회
		@GetMapping("likeProj/{userId}") //proj/userId
		public ResponseEntity<List<HashMap<String, Object>>> getLikeProj(@PathVariable("userId") String userId, ProjVO vo,Criteria cri) {
		log.info("유저가 좋아요 한 프로젝트 목록 api");
		
		cri.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getLikeProj(userId,cri);
		return ResponseEntity.ok(projList);
		}
// 좋아요 순 프로젝트 리스트
		@GetMapping("/bestProj") 
		public ResponseEntity<List<HashMap<String, Object>>> getbestProj(ProjVO vo,Criteria criteria) {
		log.info("좋아요 순 프로젝트 목록 api");
		
		criteria.setPageNum(1);
		List<HashMap<String, Object>> projList = projService.getBestProj(criteria);
		return ResponseEntity.ok(projList);
		}
}
