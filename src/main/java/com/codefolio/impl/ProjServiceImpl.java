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


@Service
@Slf4j
public class ProjServiceImpl implements ProjService {
    @Autowired
    ProjMapper projMapper;

    @Override
    public List<ProjVO> getProjList() {
        return projMapper.getProjList();
    }

    @Override
    public void addProj(Map<String, Object> param) {
        projMapper.addProj(param);
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

}
