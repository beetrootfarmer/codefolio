package com.codefolio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import com.codefolio.service.FileService;
import com.codefolio.vo.FileVO;
import com.codefolio.utils.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileService fileService;


    @GetMapping("hello")
        public String hello(){
            return "helloTest";
        }

// [프로젝트 추가] 파일추가
        @GetMapping("/uploadFiles")
        public Iterable<FileVO> getUploadFileList(){
            return fileService.getFileList();
        }

        @GetMapping("/upload/{fileSeq}")
        public Optional<FileVO> getUploadFile(@PathVariable int fileSeq){
            return fileService.getUploadFile(fileSeq);
        }

 @PostMapping("/upload")
        public List<FileVO> insertFile(FileVO vo, HttpServletRequest request,
                MultipartHttpServletRequest mhsr) throws IOException  {

            int fileSeq = fileService.getFileSeq();
            FileUtils fileUtils = new FileUtils();

            List<FileVO> fileList = fileUtils.parseFileInfo(fileSeq, request, mhsr);

            if(CollectionUtils.isEmpty(fileList) == false) {
                fileService.saveFile(fileList);
                System.out.println("saveFile()탐 + fileList===" + fileList);
                }
            return fileList;
        }


//         @GetMapping("/downloadFile/{fileName}")
//        public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//         Resource resource = fileService.selectFile(fileName);
//
//         String fileType = null;
//         try {
//             fileType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//         } catch (IOException ex) {
//             logger.info("Could not determine file type.");
//         }
//
//         // Fallback to the default content type if type could not be determined
//         if(fileType == null) {
//             fileType = "application/octet-stream";
//         }
//         return ResponseEntity.ok()
//                 .contentType(MediaType.parseMediaType(fileType))
//                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                 .body(resource);
//   }



    }

