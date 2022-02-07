package com.codefolio.impl;

import com.codefolio.mapper.ProjMapper;
import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
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

}
