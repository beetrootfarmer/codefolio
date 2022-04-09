package com.codefolio.mapper;

import java.util.HashMap;
import java.util.List;

import com.codefolio.vo.Criteria;
import com.codefolio.vo.ProjVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;


// @Mapper Autowired에 연결하기 위한 어노테이션
@Mapper
public interface ProjMapper {
    
    public void addProj(ProjVO vo);

    public int getTotalProj();

    public ProjVO getProjDetail(@Param("projSeq") int projSeq);

    public int getProjSeq();

    public void deleteProj(int seq);

    public void update(ProjVO vo);

    public void viewUp(int projSeq);
    
    public int selectProjCount(ProjVO vo);
    
    public List<ProjVO> getProjListwithCri(Criteria cri);

	public List<ProjVO> getProjListOri();

	public List<HashMap<String, Object>> getProjandFile();

//	public List<ProjVO> searchProj(@Param("keyword")String keyword);

	public List<HashMap<String, Object>> searchProj(String keyword);
	
	public List<HashMap<String, Object>> getProjList(Map<String, Object> paramMap);

	public List<HashMap<String, Object>> getLikeProj(Map<String, Object> likeProj);

	public List<HashMap<String, Object>> getBestProj(Criteria cri);

    public void updatePreview(int projSeq, String preview);

    List<ProjVO> getProjByUser(String userId);
}