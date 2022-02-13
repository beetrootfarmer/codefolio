package com.codefolio.controller;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    //userController test
    @RequestMapping("/")
    public String hello() {
            return "user/user-main";
    }

    //page url
    @RequestMapping("/user/join")
    public String showJoin(){
        return "user/user-join";
    }

    @RequestMapping("/user/login")
    public String showLogin(){return "user/user-login";}

    @RequestMapping("/user/update")
    public String showUpdate(Model model, int userSeq){
        UserVO user = userService.getUser(userSeq);
        model.addAttribute("user",user);
        return "user/user-update";
    }

    //JoinUser
    @RequestMapping("/user/joinUser")
    @ResponseBody
    //HttpServletRequest req;
    public String joinUser(@RequestParam Map<String, Object> param){
        userService.joinUser(param);

        String msg = "회원가입이 완료되었습니다.";

        StringBuilder sb = new StringBuilder();

        sb.append("alert('"+msg+"');");
        sb.append("location.replace('./list');");

        sb.insert(0,"<script>");
        sb.append("</script>");

        return sb.toString();
    }

    //Get user(userSeq)
    @RequestMapping("/user/detail")
    public String getUser(Model model, int userSeq){
        UserVO user = userService.getUser(userSeq);
        model.addAttribute("user",user);
        System.out.println("=========user 상세============"+"\n" + userService.getUser(userSeq));
        return "user/user-detail";
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

    //Update user
    @RequestMapping("/user/updateUser")
    @ResponseBody
    public String updateUser(@RequestParam Map<String,Object> param, int userSeq){
        System.out.println("user/update");

        userService.updateUser(param);

        String msg = userSeq+"번 사용자가 수정되었습니다.";

        StringBuilder sb = new StringBuilder();

        sb.append("alert('"+msg+"');");
        sb.append("location.replace('./detail?userSeq="+userSeq+"');");

        sb.insert(0,"<script>");
        sb.append("</script>");

        return sb.toString();
    }


    @RequestMapping("/user/delete")
    @ResponseBody
    public String deleteUser(int userSeq){
        userService.delete(userSeq);

        String msg = userSeq+"번 사용자가 삭제되었습니다.";

        StringBuilder sb = new StringBuilder();

        sb.append("alert('"+msg+"');");
        sb.append("location.replace('./list');");

        sb.insert(0,"<script>");
        sb.append("</script>");

        return sb.toString();
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


}