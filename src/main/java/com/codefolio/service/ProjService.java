package com.codefolio.service;

import com.codefolio.vo.ProjVO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;


public interface ProjService {
    @Transactional
    public List<ProjVO> getProjList();

    //hweyoung udpate
    @Transactional
    public List<ProjVO> getProjByUser(String userId);

     @Transactional
//     public void addProj(Map<String, Object> param);
     public void addProj(ProjVO vo);

     @Transactional
    public int getTotalProj();

     @Transactional
    public ProjVO getProjDetail(int projSeq);

     @Transactional
    public int getProjSeq();

     @Transactional
    public void deleteProj(int projSeq);

     @Transactional
    public void update(Map<String, Object> param);

     //hweyoung update
     @Transactional
     public void updatePreview(int projSeq, String preview);

     @Transactional
    public void viewUp(int projSeq);
}
