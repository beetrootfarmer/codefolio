package com.codefolio.mapper;

import java.util.List;
import com.codefolio.vo.ProjVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// @Mapper Autowired에 연결하기 위한 어노테이션
@Mapper
public interface ProjMapper {
    public List<ProjVO> getProjList();
}