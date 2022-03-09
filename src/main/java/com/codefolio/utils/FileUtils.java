package com.codefolio.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.codefolio.vo.FileVO;

public class FileUtils {
	
	private boolean add;

    public List<FileVO> parseFileInfo(int typeSeq, String boardtype, HttpServletRequest request,
			MultipartHttpServletRequest mhsr) throws IOException {
		
		System.out.println(mhsr.toString());
		if(ObjectUtils.isEmpty(mhsr)) {
			return null;
		}
		
		List<FileVO> fileList = new ArrayList<FileVO>();
		
		String root_path = request.getSession().getServletContext().getRealPath("/");

		String attach_path = "upload/";

		String board_path = boardtype + "/";

		System.out.println("FileUtils+ file Path 를 왜 못찾을까요오");
		File file = new File(root_path + attach_path+ board_path);
		// file.exists : 저장된 파일이 있는지 확인 
		if(file.exists() == false) {
			// mkdirs : 만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 생성
			file.mkdirs();
		}
		
		// Iterator : 반복자. 읽어오는 방법 표준화.
		// 파일 이름을 읽어옴
		Iterator<String> iterator = mhsr.getFileNames();
		System.out.println("fileutils의 iterator..mhsr.getFileNames()=============" + mhsr.getFileNames());
		while(iterator.hasNext()) {
			List<MultipartFile> list = mhsr.getFiles(iterator.next());
	
			
			// 파일 사이즈가 0보다 크다면 (업로드되었다면) 파일 주소를 조합해서 새로 만든후 file에 저장
			for(MultipartFile mf : list) {
				if(mf.getSize() > 0) {
					FileVO vo = new FileVO();
					vo.setBoardSeq(typeSeq);
					vo.setSize(mf.getSize());
					vo.setFileName(mf.getOriginalFilename());
					vo.setBoardType(boardtype);
					vo.setFileDownloadUri(root_path + attach_path+ board_path+mf.getOriginalFilename());
					add = fileList.add(vo);

					System.out.println("fileutils의 for문..FileVO"+ vo);
					
					file = new File(root_path + attach_path +board_path+mf.getOriginalFilename());
					mf.transferTo(file);
				} else {
					fileList = null;
				}
			}
		}
		return fileList;
	}

}


