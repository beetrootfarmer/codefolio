package com.codefolio.controller;

import com.codefolio.config.security.JwtTokenProvider;
import com.codefolio.dto.ErrorResponse;
import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
        System.out.println(user);
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
    public Object loginUser(@RequestBody UserVO user, ServletRequest request){
        UserVO userDetail = userService.getUser(user.getId());
        System.out.println(userDetail);
        if(userDetail.getId()==null)throw new IllegalArgumentException("가입되지 않은 ID 입니다.");
        //TODO : password does not match
        if(!passwordEncoder.matches(user.getPwd(),userDetail.getPwd()))
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");

        String getActoken = jwtTokenProvider.resolveToken((HttpServletRequest)request);
        String getReftoken=userService.getUserById(user.getId()).getRefToken();
        System.out.println("====getAccessToken==="+getActoken);
        System.out.println("====getRefreshToken==="+getReftoken);

        //유효한 토큰인지 확인합니다.
//        case1: access token과 refresh token 모두가 만료된 경우 -> 에러 발생
//        case2: access token은 만료됐지만, refresh token은 유효한 경우 ->  access token 재발급
//        case3: access token은 유효하지만, refresh token은 만료된 경우 ->  refresh token 재발급
//        case4: accesss token과 refresh token 모두가 유효한 경우 -> 다음 미들웨어로

        if (getActoken != null && jwtTokenProvider.validateToken(getActoken)) {
            if(getReftoken!=null)
                System.out.println("token 유효함");
        }
        else{
            System.out.println("accessToken 만료");
        }

        String newAcToken = jwtTokenProvider.createToken(userDetail.getId(),userDetail.getRole());
        if(newAcToken==null) return new ErrorResponse(500,"Exception","토큰 발행 실패");


        return newAcToken;
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
