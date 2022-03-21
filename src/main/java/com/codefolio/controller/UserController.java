package com.codefolio.controller;

import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.ErrorResponse;
import com.codefolio.service.MailService;
import com.codefolio.service.UserService;
import com.codefolio.vo.MailTO;
import com.codefolio.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<Object> joinUser(@RequestBody UserVO user){
        log.info("====join===");
        System.out.println(user);
        user.setRole("ROLE_USER");
        //TODO : saltkey 설정
//        String salt = //salt key 설정;
//        String userPwd = user.getPwd()+salt;
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
        log.info("===login===");
        UserVO userDetail = userService.getUser(user.getEmail());
        if(userDetail.getEmail()==null) new ErrorResponse(404,"NOT_FOUND","가입되지 않은 Email입니다.");
        if(!passwordEncoder.matches(user.getPwd(),userDetail.getPwd())){
            log.error("[ERROR] Wrong Password");
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }


        String getActoken = jwtTokenProvider.resolveToken((HttpServletRequest)request);
        String getReftoken= userDetail.getRefToken();

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

        String newAcToken = jwtTokenProvider.createToken(userDetail.getEmail());
        if(newAcToken==null) return new ErrorResponse(500,"Exception","토큰 발행 실패");
        String newRefToken = jwtTokenProvider.createRefToken(userDetail.getEmail());

        return newAcToken;
    }

    //회원가입 email의 중복성 체크
    //TODO: response json
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody UserVO user){
        int result= userService.checkEmail(user.getEmail());
        if(result!=0)return ResponseEntity.badRequest().body("fail"+result);
        else return ResponseEntity.ok("success");
    }

    //회원가입 name의 중복성 체크
    //TODO: response json
    @PostMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestBody UserVO user){
        int result = userService.checkId(user.getId());
        if(result!=0)return ResponseEntity.badRequest().body("fail");
        else return ResponseEntity.ok("success");
    }

    //Get userlist
    @GetMapping("/admin/list")
    public ResponseEntity<List<UserVO>> getAllUserData() {
        List<UserVO> userList =  userService.getAllUserData();
        return ResponseEntity.ok(userList);
    }

    //email 인증 => userEmail 입력
    //email 인증 후 문자열 비교는 front에
    @PostMapping("/mailConfirm")
    @ResponseBody
    public ResponseEntity<String> confirmMail(@RequestBody UserVO user) {
        String userEmail=user.getEmail();
        String userId = user.getId();
        MailTO mailTO = new MailTO(userId,userEmail);
        mailService.checkEmail(mailTO);
        return ResponseEntity.ok(mailTO.getRString());
    }


    @PostMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestBody UserVO user){
        UserVO userDetail = userService.getUser(user.getEmail());
        System.out.println(user.getEmail());
        System.out.println(userDetail.getEmail());
        if(userDetail.getEmail().isEmpty())
            return new ErrorResponse(404, "NOT_FOUND","해당 유저를 찾을 수 없습니다.");
        MailTO mailTO = new MailTO(userDetail.getId(),userDetail.getEmail());
        //이메일 토큰 만료 시간 5분
        String acToken = jwtTokenProvider.createEmailToken(userDetail.getEmail());
        mailService.changePwd(mailTO, acToken);
        mailTO.setrString(acToken);

        return ResponseEntity.ok(mailTO.getRString());
    }


//    @PostMapping()
//    public MailTO sendQuestion(@RequestBody UserVO user, ){
//        //TODO : 고객 문의 메일로
//    }


}

