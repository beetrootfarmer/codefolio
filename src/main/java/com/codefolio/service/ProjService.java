package com.codefolio.service;

import com.codefolio.vo.Criteria;
import com.codefolio.vo.ProjVO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


public interface ProjService {

    @Transactional
    public void addProj(ProjVO vo);

    @Transactional
    public int getTotalProj();

    @Transactional
    public ProjVO getProjDetail(int projSeq);

    @Transactional
    public int getProjSeq();

    @Transactional
    public void deleteProj(int seq);

    @Transactional
    public void update(ProjVO vo);

    @Transactional
    public void viewUp(int projSeq);

    public int selectProjCount(ProjVO vo);

    public List<HashMap<String, Object>> searchProj(String keyword);

    @Transactional
    public List<HashMap<String, Object>> getProjList(String keyword, Criteria cri);

    @Transactional
    public List<HashMap<String, Object>> getLikeProj(String userId, Criteria cri);

    @Transactional
    public List<HashMap<String, Object>> getBestProj(Criteria criteria);

    //hweyoung update
    @Transactional
    public void updatePreview(int projSeq, String preview);

    //hweyoung udpate
    @Transactional
    public List<ProjVO> getProjByUser(String userId);

    public String makeThumbnail(MultipartFile tn) throws IOException;
}