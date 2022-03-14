package com.codefolio.controller;

import com.codefolio.config.security.JwtTokenProvider;
import com.codefolio.dto.ErrorResponse;
import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SecurityController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/")
    public String mainhome(){
        return "mainhome";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<Object> joinUser(@RequestBody UserVO user){
        user.setRole("ROLE_USER");
        String encUserPwd = passwordEncoder.encode(user.getPwd());
        System.out.println("encodig PWD : "+encUserPwd);
        user.setPwd(encUserPwd);   //encoding된 password 넣기
        Integer userSeq = userService.joinUser(user);
        UserVO userDetail = userService.getUser(user.getId());
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginUser(@RequestBody UserVO user){
        UserVO userDetail = userService.getUser(user.getId());
        System.out.println(userDetail);
        if(userDetail.getId()==null)throw new IllegalArgumentException("가입되지 않은 ID 입니다.");
        //TODO : password does not match
        if(!passwordEncoder.matches(user.getPwd(),userDetail.getPwd()))
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        return jwtTokenProvider.createToken(userDetail.getId(),userDetail.getRole());
    }
//
//    @PostMapping("/changePwd")
//    @ResponseBody
//    public Object changePwd(@RequestBody UserVO user){
//        UserVO userDetail = userService.getUser(user.getId());
//        if((user.getId()!=userDetail.getId())&&(user.getEmail()!=userDetail.getEmail()))
//            return new ErrorResponse(404, "NOT_FOUND","해당 유저를 찾을 수 없습니다.");
//
//    }

}
