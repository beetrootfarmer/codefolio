package com.codefolio.controller;

import com.codefolio.config.jwt.JwtTokenProvider;
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
        UserVO userDetail = userService.getUser(user.getEmail());
        return ResponseEntity.ok(userDetail);
    }

    //TODO : email로 login
    @PostMapping("/login")
    @ResponseBody
    public Object loginUser(@RequestBody UserVO user, ServletRequest request){
        UserVO userDetail = userService.getUser(user.getEmail());
        System.out.println(userDetail);
//        if(userDetail.getEmail()==null)throw new IllegalArgumentException("가입되지 않은 ID 입니다.");
        if(userDetail.getEmail()==null) new ErrorResponse(404,"NOT_FOUND","가입되지 않은 Email입니다.");
        if(!passwordEncoder.matches(user.getPwd(),userDetail.getPwd()))
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");

        String getActoken = jwtTokenProvider.resolveToken((HttpServletRequest)request);
        String getReftoken= userDetail.getRefToken();
        System.out.println("====getAccessToken==="+getActoken);
        System.out.println("====getRefreshToken==="+getReftoken);

        //유효한 토큰인지 확인합니다.
//        case1: access token과 refresh token 모두가 만료된 경우 -> 에러 발생
//        case2: access token은 만료됐지만, refresh token은 유효한 경우 ->  access token 재발급
//        case3: access token은 유효하지만, refresh token은 만료된 경우 ->  refresh token 재발급
//        case4: accesss token과 refresh token 모두가 유효한 경우 -> 다음 미들웨어로

        if (getActoken != null && jwtTokenProvider.validateToken(getActoken)) {
            System.out.println("accessToken 유효함");
            if(getReftoken!=null&&jwtTokenProvider.validateToken(getReftoken))   //refresh token유효함
                System.out.println("ac/ref 유효함");
            else
                System.out.println("ref 만료");
        }
        else{
            System.out.println("accessToken 만료");
            if(getReftoken!=null&&jwtTokenProvider.validateToken(getReftoken))   //refresh token유효함
                System.out.println("refToken 유효");
            else System.out.println("ac/ref 만료");
        }

        String newAcToken = jwtTokenProvider.createToken(userDetail.getEmail(),userDetail.getRole());
        if(newAcToken==null) return new ErrorResponse(500,"Exception","토큰 발행 실패");
        String newRefToken = jwtTokenProvider.createRefToken(userDetail.getEmail(),userDetail.getRole());
        System.out.println("newAcToken : "+newAcToken+"\nnewRefToken : "+newRefToken);



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
