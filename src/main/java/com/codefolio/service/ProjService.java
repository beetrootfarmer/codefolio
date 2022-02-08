package com.codefolio.service;

import com.codefolio.vo.ProjVO;
import java.util.List;
import java.util.Map;

public interface ProjService {
    public List<ProjVO> getProjList();
    public void addProj(Map<String, Object> param);
    public int getTotalProj();
    public ProjVO getProjDetail(int projSeq);
    public int getProjSeq();
    public void deleteProj(int projSeq);
}
