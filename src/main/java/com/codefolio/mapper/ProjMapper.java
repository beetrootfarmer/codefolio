package com.codefolio.mapper;

import java.util.List;
import com.codefolio.vo.ProjVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Map;


// @Mapper Autowired에 연결하기 위한 어노테이션
@Mapper
public interface ProjMapper {
    public List<ProjVO> getProjList();

     public void addProj(ProjVO vo);

    public int getTotalProj();

    public ProjVO getProjDetail(@Param("projSeq") int projSeq);

    public int getProjSeq();

    public void deleteProj(int seq);

    public void update(Map<String, Object> param);

    public void viewUp(int projSeq);

}