package com.codefolio.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codefolio.config.exception.NotCreateException;
import com.codefolio.dto.JsonResponse;
import com.codefolio.service.FileService;
import com.codefolio.service.ProjService;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.ProjVO;

import com.codefolio.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import com.codefolio.utils.FileUtils;
import org.springframework.util.CollectionUtils;


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


// [프로젝트 리스트 불러오기]
    @GetMapping("/list")
//    Model은 HashMap형태로 key와 value값처럼 사용
    public ResponseEntity<List<ProjVO>> getProjList() {
        List<ProjVO> projList = projService.getProjList();
        return ResponseEntity.ok(projList);
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



// [프로젝트 수정]
        @PutMapping("/{projSeq}")
                public ResponseEntity<String> showUpdate(@RequestBody Map<String, Object> param, @PathVariable("projSeq") int projSeq) {
                    projService.update(param);
                    ProjVO projDetail = projService.getProjDetail(projSeq);
                    return ResponseEntity.ok(projSeq+"번 프로젝트 수정이 완료되었습니다" + projDetail);
        	}
}
