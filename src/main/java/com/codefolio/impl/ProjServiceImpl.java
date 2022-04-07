package com.codefolio.impl;

import com.codefolio.mapper.ProjMapper;
import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ProjVO> getProjList() {
        return projMapper.getProjList();
    }

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
    public void update(Map<String, Object> param){
         projMapper.update(param);
    }

    @Override
    public void updatePreview(int projSeq, String preview){projMapper.updatePreview(projSeq,preview);}

    @Override
    public void viewUp(int projSeq){
        projMapper.viewUp(projSeq);
    };

}
