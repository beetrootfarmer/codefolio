package com.codefolio.controller;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("hello")
    public String hello(){
        return "helloTest";
    }

    //JoinUser
    @PostMapping("/join")
    public ResponseEntity<UserVO> joinUser(@RequestBody UserVO user){
        //로그인 ID의 중복성 체크
//        Map<String, Object> checkUserIdDupRs = userService.checkUserIdDup((String)param.get("userId"));
//
//        if(((String)checkUserIdDupRs.get("resultCode")).startsWith("F-")){
//            return ResponseEntity.(String)checkUserIdDupRs.get("msg");
//        }
        Integer userSeq = userService.joinUser(user);
        UserVO userDetail = userService.getUser(userSeq);
        return ResponseEntity.ok(userDetail);
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

    //Update user
    @PutMapping("/{userSeq}")
    public ResponseEntity<String> updateUser(UserVO user,@PathVariable("userSeq") int userSeq){
        userService.updateUser(user);
        UserVO userDetail = userService.getUser(userSeq);
        return ResponseEntity.ok(userSeq+"번"+userDetail);
    }


    @DeleteMapping("/{userSeq}")
    public ResponseEntity<String> deleteUser(@PathVariable("userSeq") int userSeq){
        userService.delete(userSeq);
        return ResponseEntity.ok(userSeq+"번 회원이 삭제되었습니다.");
    }

}