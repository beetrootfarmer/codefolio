package com.codefolio.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.codefolio.vo.FileVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtils {
	
	 @Autowired
	private WebApplicationContext context;
	 
	private boolean add;

//    public List<FileVO> parseFileInfo(int typeSeq, String boardtype, HttpServletRequest request,
//			MultipartHttpServletRequest mhsr) throws IOException {
//
  public List<FileVO> parseFileInfo(int typeSeq, String boardtype, MultipartFile[] file) throws IOException {
		
		if(ObjectUtils.isEmpty(file)) {
			return null;
		}
		
		
		String root_path = context.getServletContext().getRealPath("/");
		String attach_path = "upload/";
		String board_path = boardtype + "/";
		
		List<FileVO> fileList = new ArrayList<FileVO>();

		File path = new File(root_path + attach_path+ board_path);
		// file.exists : 저장된 파일이 있는지 확인 
		if(path.exists() == false) {
			// mkdirs : 만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 생성
			path.mkdirs();
		}
		for(int i=0; i<file.length; i++) { 
			if(file[i].getSize() > 0) {
				String fileOriginName = file[i].getOriginalFilename(); 
				FileVO vo = new FileVO();
				vo.setBoardSeq(typeSeq);
				vo.setSize(file[i].getSize());
				vo.setFileName(file[i].getOriginalFilename());
				vo.setBoardType(boardtype);
				vo.setFileDownloadUri(root_path + attach_path+ board_path+file[i].getOriginalFilename());
				add = fileList.add(vo);

				log.info("fileutils의 for문..FileVO"+ vo);
				File f = new File(root_path + attach_path+ board_path+fileOriginName); 
				file[i].transferTo(f); 
			} else {
				fileList = null;
			}
	
		}
		return fileList;
  }
}


