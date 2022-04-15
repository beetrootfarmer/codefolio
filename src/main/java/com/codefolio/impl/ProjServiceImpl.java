package com.codefolio.impl;

import com.codefolio.mapper.ProjMapper;
import com.codefolio.service.ProjService;
import com.codefolio.vo.Criteria;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import com.codefolio.utils.CUtil;

@Slf4j
@Service
public class ProjServiceImpl implements ProjService {
	@Autowired
	ProjMapper projMapper;

	@Autowired
	private WebApplicationContext context;

	@Override
	public List<ProjVO> getProjByUser(String userId){return projMapper.getProjByUser(userId);}

	@Override
	public void addProj(ProjVO vo) {
		projMapper.addProj(vo);
	}


	@Override
	public int getProjSeq() {
		return projMapper.getProjSeq();
	}

	@Override
	public int getTotalProj() {
		return projMapper.getTotalProj();
	}

	@Override
	public ProjVO getProjDetail(int projSeq){
		return projMapper.getProjDetail(projSeq);
	}

	@Override
	public void deleteProj(int projSeq) {
		projMapper.deleteProj(projSeq);
	}

	@Override
	public void update(ProjVO vo){
		projMapper.update(vo);
	}

	@Override
	public void viewUp(int projSeq){
		projMapper.viewUp(projSeq);
	};
	@Override
	public int selectProjCount(ProjVO vo) {
		return projMapper.selectProjCount(vo);
	}


	@Override
	public List<HashMap<String, Object>> searchProj(String keyword) {
		return projMapper.searchProj(keyword);
	}

	@Override
	public List<HashMap<String, Object>> getProjList(String keyword, Criteria cri) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("keyword", keyword);
		cri.setStartNum((cri.getPageNum() -1 ) * cri.getAmount());
		paramMap.put("criteria", cri);

		log.info("projServiceImpl의 getProjList메소드에서==== keyword, cri==="+ keyword + "," + cri);

		return projMapper.getProjList(paramMap);
	}

	@Override
	public List<HashMap<String, Object>> getLikeProj(String userId, Criteria cri) {

		Map<String, Object> likeProj = new HashMap<String, Object>();
		likeProj.put("userId", userId);

		cri.setStartNum((cri.getPageNum() -1 ) * cri.getAmount());
		likeProj.put("criteria", cri);

		return projMapper.getLikeProj(likeProj);
	}

	@Override
	public List<HashMap<String, Object>> getBestProj(Criteria criteria) {

		Map<String,Object> bestProj = new HashMap<String, Object>();
		criteria.setStartNum((criteria.getPageNum() -1 ) * criteria.getAmount());
		bestProj.put("criteria", criteria);

		return projMapper.getBestProj(bestProj);
	}


	@Override
	public void updatePreview(int projSeq, String preview){projMapper.updatePreview(projSeq,preview);}

	@Override
	public String makeThumbnail(MultipartFile tn) throws IOException {
		String uploadPath = context.getServletContext().getRealPath("/");
		String attach_path = "upload/thumbnail/";
		String fileName = tn.getOriginalFilename();

		String thumbnail = uploadPath + attach_path + fileName;
		File file = new File(uploadPath + attach_path + fileName);
		int i = 1;

		// 같은 이름의 파일이 있을 경우 돌아가는 while문
		while(file.exists()) {
			int index = fileName.lastIndexOf(".");
			String newFileName = fileName.substring(0,index)+"_"+i+fileName.substring(index);

			file = new File(uploadPath+ attach_path + newFileName);

			tn.transferTo(file);
			thumbnail = uploadPath +attach_path +newFileName;
			i++;
		}
		return thumbnail;
	}


}