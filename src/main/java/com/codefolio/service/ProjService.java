package com.codefolio.service;

import com.codefolio.vo.ProjVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjService {
    public List<ProjVO> getProjList();
}
