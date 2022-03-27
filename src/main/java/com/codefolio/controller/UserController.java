package com.codefolio.controller;

import com.codefolio.config.exception.NotCreateException;
import com.codefolio.config.exception.NotFoundException;
import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.response.GetUserResponse;
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
        String saltkey = userService.getSecString();
        String encUserPwd = passwordEncoder.encode(user.getPwd()+saltkey);
        user.setSaltKey(saltkey);
        user.setPwd(encUserPwd);   //encoding된 password 넣기
        Integer userSeq = userService.joinUser(user);
        UserVO userDetail = userService.getUserByEmail(user.getEmail());
        return ResponseEntity.ok(new JsonResponse(userDetail,200,"joinUser"));
    }

    @PostMapping("/login")
    @ResponseBody
    public Object loginUser(@RequestBody UserVO user, ServletRequest request){
        log.info("===login===");
        UserVO userDetail = userService.getUserByEmail(user.getEmail());
        System.out.println(userDetail);
        if(!userDetail.getEmail().equals(user.getEmail())) return new NotFoundException("unsigned email");
        if(!passwordEncoder.matches(user.getPwd()+userService.getSaltKey(user.getEmail()),userDetail.getPwd())){
            log.error("[ERROR] Wrong Password");
            throw new NotFoundException("password not match");
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
        if(newAcToken==null) return new NotCreateException("Unable to create token.");
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

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> getUser(@PathVariable String userId) {
        try{
            UserVO user = userService.getUserById(userId);
            GetUserResponse userDetail = GetUserResponse.builder()
                    .img(user.getImg())
                    .gitId(user.getGitId())
                    .name(user.getName())
                    .stack(user.getStack())
                    .introFile(user.getIntroFile())
                    .job(user.getJob()).build();
            return ResponseEntity.ok(new JsonResponse(userDetail,200,"getUser"));
        }catch (Exception s){
            throw new NotFoundException("NotFoundUser");
        }

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


    @PostMapping("/mailToPwd")
    @ResponseBody
    public Object mailToPwd(@RequestBody UserVO user){
        UserVO userDetail = userService.getUserByEmail(user.getEmail());
        System.out.println(user.getEmail()+userDetail.getEmail());
        if(!user.getEmail().equals(userDetail.getEmail())) throw new NotFoundException("Email not found");
        MailTO mailTO = new MailTO(userDetail.getId(),userDetail.getEmail());
        //이메일 토큰 만료 시간 5분
        String acToken = jwtTokenProvider.createEmailToken(userDetail.getEmail());
        mailService.changePwd(mailTO, acToken);
        mailTO.setrString(acToken);

        return ResponseEntity.ok(mailTO.getRString());
    }


//    @PostMapping()
//    public MailTO sendInquiry(@RequestBody UserVO user, ){
//        //TODO : 고객 문의 메일로
//    }


}

