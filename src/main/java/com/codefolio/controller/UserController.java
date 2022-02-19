package com.codefolio.controller;

import com.codefolio.service.EmailService;
import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @GetMapping("hello")
    public String hello(){
        return "helloTest";
    }

    //JoinUser
    @PostMapping("")
    public ResponseEntity<UserVO> joinUser(@RequestBody UserVO user){

        Integer userSeq = userService.joinUser(user);
        UserVO userDetail = userService.getUser(userSeq);

        return ResponseEntity.ok(userDetail);
    }


    //TODO : 이메일 중복 확인이 제대로 안됨 결과 값이 0만 뜬다.
    //Required request parameter 'email' for method parameter type UserVO is not present
    //로그인 email의 중복성 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestParam("email") UserVO userEmail){
        int result= userService.checkEmail(userEmail);
        System.out.println(result);
        if(result!=0)return ResponseEntity.badRequest().body("fail");
        else return ResponseEntity.ok("success");
    }

    //TODO: jwt로그인 방식 구현하기 필요
    //회원 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginCheck(@RequestBody UserVO user){
        String userName = userService.checkLogin(user);
        if(userName!=null)
            return ResponseEntity.ok(userName+" 로그인 성공");
        else return ResponseEntity.badRequest().body("해당 사용자를 찾을 수 없습니다.");
    }

    //Get user(userSeq)
    @GetMapping("/{userSeq}")
    public ResponseEntity<String> getUser(@PathVariable("userSeq") int userSeq){
        UserVO user = userService.getUser(userSeq);
        return ResponseEntity.ok(userSeq+"번"+user);
    }

    //Get userlist
    @GetMapping("/list")
    public ResponseEntity<List<UserVO>> getAllUserData() {
        List<UserVO> userList =  userService.getAllUserData();
        return ResponseEntity.ok(userList);
    }

    //TODO : user update시 null 값만 저장됨
    //Update user
    @PutMapping("/{userSeq}")
    public ResponseEntity<String> updateUser(@RequestBody UserVO user,@PathVariable("userSeq") int userSeq){
        userService.updateUser(user);
        UserVO userDetail = userService.getUser(userSeq);
        return ResponseEntity.ok(userSeq+"번\n"+userDetail);
    }


    @DeleteMapping("/{userSeq}")
    public ResponseEntity<String> deleteUser(@PathVariable("userSeq") int userSeq){
        userService.delete(userSeq);
        return ResponseEntity.ok(userSeq+"번 회원이 삭제되었습니다.");
    }


    @GetMapping("/emailConfirm")
    @ResponseBody
    public ResponseEntity<String> emailConfirm(){
        return ResponseEntity.ok("hello");
    }
//    public ResponseEntity<String> emailConfirm(@RequestParam("email") String email) throws Exception {
//
//        System.out.println(email);
//        String confirm = emailService.sendSimpleMessage(email);
//
//        return ResponseEntity.ok(confirm);
//    }



}