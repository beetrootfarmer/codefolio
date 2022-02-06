package com.codefolio.controller;

import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 이걸 해야 log.info(..) 가 가능, 디버깅 용도
public class ProjController {
    @Autowired
    ProjService projService;

    @RequestMapping("/proj")
//    Model은 HashMap형태로 key와 value값처럼 사용
    public String getProjList(Model aModel) {
        List<ProjVO> projList = projService.getProjList();

        aModel.addAttribute("projList", projList);
        aModel.addAttribute("test","el표기 테스트");

            log.info("===============projList : " + projList);
            System.out.println("==syso=============projList : " + projList);
            return "proj-list";
    }

}
