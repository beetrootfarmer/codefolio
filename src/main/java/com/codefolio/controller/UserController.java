package com.codefolio.controller;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

      //    userController test
        @GetMapping("/join")
        public String hello() {
            return "Welcome Codefolio";
        }

    @RequestMapping("/user")
    public String getAllUserData(Model aModel) {
        List<UserVO> userList = userService.getAllUserData();

        aModel.addAttribute("userList", userList);

        System.out.println("=========test 메소드 탐============");
        System.out.println("=========user 정보============" + userService.getAllUserData());
        return "user-list";
    }

}