package com.codefolio.controller;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    //userController test
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
            return "Welcome Codefolio";
    }

    //회원가입 폼
    @RequestMapping("/user/join")
    public String showJoin(){
        return "user/user-join";
    }

    //회원가입 폼 구현할때 보기
//    @RequestMapping("/user/doJoin")
//    @ResponseBody
//    public String doJoin(@RequestParam Map<String, Object> param,Model model){
//        //로그인 ID의 중복성 체크
//        Map<String, Object> checkUserIdDupRs = userService.checkUserIdDup((String)param.get("userId"));
//
//        if(((String)checkUserIdDupRs.get("resultCode")).startsWith("F-")){
//            model.addAttribute("alertMsg",checkUserIdDupRs.get("msg"));
//            model.addAttribute("historyBack",true);
//            return "user/redirect";
//        }
//        return "";
//    }


    //JoinUser
    @RequestMapping("/user/doJoin")
    @ResponseBody
    //HttpServletRequest req;
    public String doJoin(@RequestParam Map<String, Object> param){
        userService.JoinUser(param);
        return "게시물이 추가되었습니다.";
    }


    public String getUser(Model model){
        UserVO user = userService.getUser();
        model.addAttribute("user",user);
        return "user/user-join";
    }



    //Get userlist => model view
    @RequestMapping("/user/list")
    public String getAllUserData(Model model) {
        List<UserVO> userList = userService.getAllUserData();

        model.addAttribute("userList", userList);
        //request.setAttribute("userList",userList); 와 똑같은 표현

        System.out.println("=========test 메소드 탐============");
        System.out.println("=========user 정보============"+"\n" + userService.getAllUserData());
        return "user/user-list";
    }



}