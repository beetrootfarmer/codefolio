package com.codefolio.impl;

import com.codefolio.mapper.ProjMapper;
import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjServiceImpl implements ProjService {
    @Autowired
    ProjMapper projMapper;

    public List<ProjVO> getProjList() {
        return projMapper.getProjList();
    }
}
