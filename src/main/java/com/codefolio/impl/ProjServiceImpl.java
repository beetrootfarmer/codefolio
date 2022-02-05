package com.codefolio.impl;

import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjServiceImpl implements ProjService {
    public List<ProjVO> getProjList() {
//        테스트용 임시 데이터 주입
        ProjVO proj1 = new ProjVO(1, "사용자1", "코드폴리오", "beetrootfarmer", 1, 111, "Java, MySQL");
        ProjVO proj2 = new ProjVO(2, "사용자2", "코드폴리오", "beetrootfarmer", 2, 111, "Java, Mybatis");
        ProjVO proj3 = new ProjVO(3, "사용자3", "코드폴리오", "beetrootfarmer", 3, 111, "Java, SringBoot");

        List<ProjVO> projList = new ArrayList<>();

        projList.add(proj1);
        projList.add(proj2);
        projList.add(proj3);

        return projList;
    }
}
