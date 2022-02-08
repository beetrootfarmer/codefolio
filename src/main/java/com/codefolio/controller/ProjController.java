package com.codefolio.controller;

import com.codefolio.service.ProjService;
import com.codefolio.vo.ProjVO;
import com.codefolio.mapper.ProjMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 이걸 해야 log.info(..) 가 가능, 디버깅 용도
public class ProjController {
    @Autowired
    ProjService projService;

// [프로젝트 리스트 불러오기]
    @RequestMapping("/proj")
//    Model은 HashMap형태로 key와 value값처럼 사용
    public String getProjList(Model model) {
        List<ProjVO> projList = projService.getProjList();
        int totalProj = projService.getTotalProj();

        model.addAttribute("projList", projList);
        model.addAttribute("totalProj",totalProj);

            return "proj-list";
    }

// [프로젝트 상세 페이지]
    @RequestMapping("/projdetail")
    public String showProjDetail(@RequestParam int projSeq, Model model) {
        model.addAttribute("proj", projService.getProjDetail(projSeq));
            return "proj-detail";
    }


// [프로젝트 추가]
        @RequestMapping("/projnew")
        public String newProj() {
                return "proj-new";
        }

        @RequestMapping("/doAdd")
        @ResponseBody
        public String doAdd(@RequestParam Map<String,Object> param) {
//              projSeq값 꺼내오기
                int projSeq = projService.getProjSeq();

//              맵에 담긴 요청값을 서비스의 add()로 넘겨줌
                projService.addProj(param);

//              StringBuilder로 게시물 추가 alert + 페이지 이동
                String msg = projSeq + "번 게시물이 추가되었습니다.";

                StringBuilder sb = new StringBuilder();

                sb.append("alert('" + msg + "');");
                sb.append("location.replace('./projdetail?projSeq=" + projSeq + "');");
//                 return sb.toString();
                return sb.toString();
}
// [프로젝트 삭제]
        @RequestMapping("/projdelete")
        @ResponseBody
        public String deleteProj(int projSeq) {
            projService.deleteProj(projSeq);

            String msg = "게시물이 삭제되었습니다.";

            StringBuilder sb = new StringBuilder();

            sb.append("alert('" + msg + "');");
            sb.append("location.replace('./proj');");

            return sb.toString();
            }


// [프로젝트 수정]
        @RequestMapping("/projupdate")
                public String updateProj() {
                        return "proj-update";
                }
}
