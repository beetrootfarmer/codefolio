package com.codefolio.controller;

import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 이걸 해야 log.info(..) 가 가능, 디버깅 용도
public class ProjController {
    @Autowired
    ProjService projService;

// 프로젝트 리스트 불러오기
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

// 프로젝트 추가
        @RequestMapping("/projnew")
        public String newProj() {
                return "proj-new";
        }

        @RequestMapping("/doAdd")
        @ResponseBody
        public String doAdd(@RequestParam Map<String,Object> param) {
//         맵에 담긴 요청값을 서비스의 add()로 넘겨줌
                projService.addProj(param);

                return "게시물이 추가되었습니다.";
        }
// 프로젝트 삭제
        @RequestMapping("/projdelete")
                public String deleteProj() {
                        return "proj-delete";
                }

        @RequestMapping("/projupdate")
                public String updateProj() {
                        return "proj-update";
                }
}
