package com.codefolio.controller;

import java.io.IOException;
import com.codefolio.service.MailService;
import com.codefolio.service.UserService;
import com.codefolio.vo.MailTO;
import com.codefolio.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

// import com.codefolio.config.CEmailSigninFailedException;
// import com.codefolio.config.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    // // JwT 변수 선언
    // private PasswordEncoder passwordEncoder;

    @GetMapping("hello")
    public String hello(){
        return "helloTest";
    }
    

    //JoinUser
    @PostMapping("")
    public ResponseEntity<UserVO> joinUser(@RequestBody UserVO user){
        Integer userSeq = userService.joinUser(user);
        UserVO userDetail = userService.getUser(user.getEmail());
        return ResponseEntity.ok(userDetail);
    }


    //회원가입 email의 중복성 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody UserVO user){
        int result= userService.checkEmail(user.getEmail());
        if(result!=0)return ResponseEntity.badRequest().body("fail"+result);
        else return ResponseEntity.ok("success");
    }

    //회원가입 name의 중복성 체크
    @PostMapping("/checkName")
    public ResponseEntity<String> checkName(@RequestBody UserVO user){
        int result = userService.checkName(user.getName());
        if(result!=0)return ResponseEntity.badRequest().body("fail"+result);
        else return ResponseEntity.ok("success");
    }

    //TODO: jwt로그인 방식 구현하기 필요
    //회원 로그인
    @PostMapping("/login2")
    public ResponseEntity<String> loginCheck(@RequestBody UserVO user){
        String userName = userService.checkLogin(user);
        if(userName!=null)
            return ResponseEntity.ok(userName+" 로그인 성공");
        else return ResponseEntity.notFound().build();
    }

    //Get user(userName으로 유저 조회)
    @GetMapping("/detail/{userName}")
    @ResponseBody
    public ResponseEntity<String> getUser(@PathVariable String userName){
        UserVO userDetail = userService.getUser(userName);
        return ResponseEntity.ok(userName+"의 사용자 조회"+userDetail);
    }

    //DeleteUser => name조회
    @DeleteMapping("/detail/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable String userName){
        userService.delete(userName);
        return ResponseEntity.ok(userName+" 회원이 삭제되었습니다.");
    }


    // [회원가입, 로그인] + [security]
    //JoinUser
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserVO user) throws IOException {

        user.setRole("ROLE_USER");
        String getPwd = user.getPwd();
        String encodedPwd = bCryptPasswordEncoder.encode(getPwd);
        user.setPwd(encodedPwd);
        Integer userSeq = userService.joinUser(user);
        UserVO userDetail = userService.getUser(user.getEmail());
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody UserVO user){
        System.out.println("로그인 폼 탐===========================");
        System.out.println("유저 정보 ==========================="+user);
//         String userChecked = userService.checkLogin(user);
        if(user!=null){
            userService.secLogin(user);

//             쿠키 방식으로 userId를 저장해주는 부분 => JWT로 적용
//             		if (resultCode.startsWith("S-")) {
//             			 CookieUtil.setAttribute(response, "uerId", userId.getUserId() + "");
//             		 	}
            return ResponseEntity.ok(user+" 로그인 성공");
        }else return ResponseEntity.notFound().build();
    }
//     구글 로그인 어디로 가야하나?
//     https://accounts.google.com/o/oauth2/auth?client_id=1028936851460-26pork2k5rdjfc23kh7q4ugp0ab11pi5.apps.googleusercontent.com&redirect_uri=https://localhost:8765/auth/google/callback&response_type=code&scope=https://www.googleapis.com/auth/drive.metadata.readonly


}