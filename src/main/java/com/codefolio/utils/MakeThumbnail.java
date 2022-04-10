package com.codefolio.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.codefolio.controller.ProjController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MakeThumbnail {
	
	@Autowired
	private WebApplicationContext context;
	
	public String makeUri(MultipartFile thumbnail) {
		log.info("여기들어오긴했어?");
		log.info("당연하지");
		String uploadPath = context.getServletContext().getRealPath("/");
		String attach_path = "upload/";
		String fileName = thumbnail.getOriginalFilename();
		
		String tn = uploadPath + attach_path + fileName;
		File file = new File(uploadPath + attach_path + fileName);
		int i = 1;
		
		// 같은 이름의 파일이 있을 경우 돌아가는 while문 
		while(file.exists()) {
			int index = fileName.lastIndexOf(".");
			String newFileName = fileName.substring(0,index)+"_"+i+fileName.substring(index);
			
			file = new File(uploadPath+ attach_path + newFileName);
			tn = uploadPath +attach_path +newFileName;
			i++;
		}
		return tn;
		
	}
	
}