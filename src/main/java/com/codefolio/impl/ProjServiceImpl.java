package com.codefolio.impl;

import com.codefolio.mapper.ProjMapper;
import com.codefolio.service.ProjService;
import com.codefolio.vo.Criteria;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void update(ProjVO proj){
         projMapper.update(proj);
    }

    @Override
    public void updatePreview(int projSeq, String preview){projMapper.updatePreview(projSeq,preview);}

    @Override
    public void viewUp(int projSeq){
        projMapper.viewUp(projSeq);
    };
    @Override
	public int selectProjCount(ProjVO vo) {
		return projMapper.selectProjCount(vo);
	}


	@Override
	public List<ProjVO> getProjListOri() {
		return projMapper.getProjListOri();
	}


	@Override
	public List<HashMap<String, Object>> getProjandFile() {
		return projMapper.getProjandFile();
	}


//	@Override
//	public List<ProjVO> searchProj(String keyword) {
//		return projMapper.searchProj(keyword);
//	}


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
	    public List<HashMap<String, Object>> getBestProj(Criteria cri) {
			return projMapper.getBestProj(cri);
	    }

}
