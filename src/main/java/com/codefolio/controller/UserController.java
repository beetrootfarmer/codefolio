package com.codefolio.controller;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private UserService userService;

    //    userController test
    @GetMapping("/join")
    public String hello() {
        return "Welcome Codefolio";
    }

    @GetMapping("/test")
    public List<UserVO> test() {
        System.out.println("=========test 메소드 탐============");
        System.out.println(userService.getAllUserData());
        return userService.getAllUserData();
    }
}
