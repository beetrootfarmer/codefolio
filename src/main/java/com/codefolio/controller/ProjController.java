package com.codefolio.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codefolio.service.FileService;
import com.codefolio.service.ProjService;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.ProjVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// 파일관련..
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import com.codefolio.utils.FileUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/proj")
public class ProjController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    ProjService projService;

    @Autowired
    FileService fileService;

    @GetMapping("hello")
        public String hello(){
            return "helloTest";
        }


// [프로젝트 리스트 불러오기]
    @GetMapping("")
//    Model은 HashMap형태로 key와 value값처럼 사용
    public ResponseEntity<List<ProjVO>> getProjList() {
        List<ProjVO> projList = projService.getProjList();

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
        System.out.println("fileVO==========" + file);
               if (file.getBoardSeq() == seq) {
                   //          fileList 안불러와진다!
                   return ResponseEntity.ok(proj + "+ " + fileList + "+file=" + file);
               }
           }
                   System.out.println("fileList==========" + fileList);

           return ResponseEntity.ok(proj + "+ " + fileList );
    }
//// [프로젝트 상세 페이지]
//    @GetMapping("/{projSeq}")
//    public ResponseEntity<String> showProjDetail(@PathVariable("projSeq") int seq) {
//
//       // 보드 시퀀스로 파일리스트 가져오기
//       List<FileVO> fileList = fileService.getFileListBySeq(seq);
//       // 조회수 늘리기
//       projService.viewUp(seq);
//       ProjVO proj = projService.getProjDetail(seq);
//
//
//
////         model.addAttribute("proj", projService.getProjDetail(projSeq));
//            return ResponseEntity.ok(seq+"번"+proj + "fileList=" + fileList);
////                             .contentType(MediaType.parseMediaType(fileType))
////                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
////                             .body(resource);
//    }


// [프로젝트 추가] + [파일 추가]
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
        public ResponseEntity<String> deleteProj(@PathVariable("projSeq") int seq) {
            fileService.deleteFileBySeq(seq);
            projService.deleteProj(seq);
            ProjVO projDetail = projService.getProjDetail(seq);
            return ResponseEntity.ok(seq+"번 프로젝트가 삭제되었습니다");
            }


// [프로젝트 수정]  + [파일 수정]
        @PutMapping("update/{projSeq}")
                public ResponseEntity<?> showUpdate(@PathVariable("projSeq") int seq,
                                                    ProjVO vo,HttpServletRequest request,
                                                    MultipartHttpServletRequest mhsr) throws IOException {

                    // 보드 시퀀스로 파일 삭제 후 새로 받아온 파일 넣어주기 
                    fileService.deleteFileBySeq(seq);
                    
                    int fileSeq = fileService.getFileSeq();
                    FileUtils fileUtils = new FileUtils();
                    List<FileVO> fileList = fileUtils.parseFileInfo(seq, request, mhsr);

                    if(CollectionUtils.isEmpty(fileList) == false) {
                        fileService.saveFile(fileList);
                        System.out.println("saveFile()탐 + fileList===" + fileList);
                        }


                    projService.update(vo);
                    ProjVO projDetail = projService.getProjDetail(seq);
                    return ResponseEntity.ok(seq+"번 프로젝트 수정이 완료되었습니다" + projDetail + fileList);
        	}
}
