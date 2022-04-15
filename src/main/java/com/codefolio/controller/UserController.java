package com.codefolio.controller;

import com.codefolio.config.exception.controller.GlobalException;
import com.codefolio.config.exception.controller.MethodNotAllowedException;
import com.codefolio.config.exception.controller.NotFoundException;
import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.request.*;
import com.codefolio.dto.response.GetUserResponse;
import com.codefolio.dto.response.UserResponse;
import com.codefolio.service.MailService;
import com.codefolio.service.ProjService;
import com.codefolio.service.UserService;
import com.codefolio.utils.UuidUtil;
import com.codefolio.vo.ProjVO;
import com.codefolio.vo.UserVO;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final MailService mailService;

    private final JwtTokenProvider jwtTokenProvider;

    private final ProjService projService;

//    @Operation(summary = "test hello", description = "hello api example")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK !!"),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
//    })


    @GetMapping("")
    public String hello(){
        return "Hello World!!";
    }


    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<Object> joinUser(@RequestBody JoinUserForm joinForm)throws Exception{
        try{
            log.info("====join===");
            log.error("join error test");
            UserVO user = UserVO.builder().id(joinForm.getId()).email(joinForm.getEmail()).name(joinForm.getName()).gitId(joinForm.getGitId()).build();
            user.setUUID(UuidUtil.generateType1UUID());
            String saltkey = userService.getSecString();
            String encUserPwd = passwordEncoder.encode(joinForm.getPwd()+saltkey);
            user.setSaltKey(saltkey);
            user.setPwd(encUserPwd);   //encoding된 password 넣기
            userService.joinUser(user);
            return ResponseEntity.ok(new JsonResponse(null,"success",200,"joinUser"));
        }catch (NullPointerException e){
            throw new NotFoundException("NullPointerException");
        }catch (Exception e){
            throw new GlobalException("globalException");
        }

    }

    @PostMapping("/login")
    @ResponseBody
    public Object loginUser(@RequestBody LoginUserForm loginForm){
        try{
            log.info("[info]===login===");
            UserVO userDetail = userService.getUserByEmail(loginForm.getEmail());
            System.out.println(userDetail);
            if(!userDetail.getEmail().equals(loginForm.getEmail())) return new NotFoundException("unsigned email");
            if(!passwordEncoder.matches(loginForm.getPwd()+userService.getSaltKey(loginForm.getEmail()),userDetail.getPwd())){
                log.error("[ERROR] Wrong Password");
                throw new NotFoundException("password not match");
            }
            //login성공시 actoken과 reftoken 재발행
            String newAcToken = jwtTokenProvider.createToken(userDetail.getUUID());
            if(newAcToken==null) return new MethodNotAllowedException("Unable to create token.");
            String newRefToken = jwtTokenProvider.createRefToken(userDetail.getUUID());
            return ResponseEntity.ok(new JsonResponse(newAcToken,"success",200,"loginUser"));
        }catch(NullPointerException e) {
            throw new NotFoundException("not found user");
        }catch (Exception e){
            throw new GlobalException("global Exception");
        }
    }


    //TODO : refreshToken 요청
    @GetMapping("/api/auth/refToken")
    private ResponseEntity<Object> checkRefToken(HttpServletRequest request){

        String getUserUUID = getUUID(request);
        try{
            String getAcToken = jwtTokenProvider.resolveToken(request);
            getUserUUID = jwtTokenProvider.getUserPk(getAcToken);
            String newAcToken = jwtTokenProvider.createToken(getUserUUID);
//            if(!userUUID.equals(getUserUUID)) throw new NotFoundException("Invalid Access Token");
            return ResponseEntity.ok(new JsonResponse(newAcToken,"valid AcToken",200,"checkRefToken"));
        } catch(ExpiredJwtException e){
            log.info("token 유효하지 않음-userController checkRecToken");
            UserVO user = userService.getUserByUUID(getUserUUID);
            String getRefToken = user.getRefToken();
            if(getRefToken != null && jwtTokenProvider.validateToken(getRefToken,request)){
                String newRefToken = jwtTokenProvider.createRefToken(getUserUUID);
                user.setRefToken(newRefToken);
                userService.updateRefToken(user);
            }
            return ResponseEntity.ok(new JsonResponse(user.getRefToken(),"valid RefToken",200,"checkRefToken"));
        }catch (NullPointerException e) {
            throw new NotFoundException("checkAcToken");
        }
    }

    private String getUUID(HttpServletRequest request){
        try{
            String getAcToken = jwtTokenProvider.resolveToken(request);
            String userUUID=jwtTokenProvider.getUserPk(getAcToken);
            log.info(userUUID);
            return userUUID;
        }catch (NullPointerException e){
            throw new NotFoundException("Invalid Access Token");
        }
    }

    //TODO : Error message not_found가 맞는지?
    //회원가입 email의 중복성 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<Object> checkEmail(@RequestBody ToEmailForm userEmail){
        int result= userService.checkEmail(userEmail.getEmail());
        if(result!=0)throw new MethodNotAllowedException("count User : "+result);
        else return ResponseEntity.ok(new JsonResponse(null,"success",200,"checkEmail"));
    }


    //회원가입 name의 중복성 체크
    @PostMapping("/checkId")
    public ResponseEntity<Object> checkId(@RequestBody ToIdForm userId){
        int result = userService.checkId(userId.getId());
        if(result!=0)throw new MethodNotAllowedException("count User : "+result);
        else return ResponseEntity.ok(new JsonResponse(null,"success",200,"checkId"));
    }

    //Get userlist
    @GetMapping("/admin/list")
    public ResponseEntity<List<UserVO>> getAllUserData() {
        List<UserVO> userList =  userService.getAllUserData();
        return ResponseEntity.ok(userList);
    }


    //TODO : user별 project list=> total proj view
    //TODO : user별 follow list
    //TODO : user별 좋아한 프로젝트
    //getUser의 경우 유저에 따라 다르게 보여줘야하는 정보가 따로 없어 하나의 api로 만들었습니다.
    @GetMapping("/{userId}")
    @ResponseBody
    public Object getUser(@PathVariable String userId, HttpServletRequest request) {
        try {
            UserVO user = userService.getUserById(userId);
            List<ProjVO> proj = projService.getProjByUser(userId);

            UserResponse userDetail = new UserResponse(user);
            GetUserResponse data = new GetUserResponse(userDetail, proj);
            return ResponseEntity.ok(new JsonResponse(data,"success",200,"getUser"));
        }catch (NullPointerException e){
            throw new NotFoundException("not found User");
        }catch (Exception e){
            throw new GlobalException("global Exception");
        }
    }


    @PostMapping("/mailConfirm")
    @ResponseBody
    public ResponseEntity<Object> confirmMail(@RequestBody ToEmailForm email)throws MessagingException, IOException {
        try{
            String seckey = userService.getSecString();


            HashMap<String, String> emailValues = new HashMap<>();
            emailValues.put("seckey",seckey);
            mailService.send("codefolio에서 전송한 이메일입니다. ",email.getEmail(), "emailConfirm", emailValues);

            return ResponseEntity.ok(new JsonResponse(seckey,"success",200,"confirmMail"));
        }catch (MessagingException e){
            log.info("Message Exception");
            throw new GlobalException("Message Exception");
        }catch (IOException e){
            log.error("IOException");
            throw new GlobalException("IOException");
        }

    }


    @PostMapping("/mailToPwd")
    @ResponseBody
    public ResponseEntity<Object> mailToPwd(@RequestBody ToEmailForm email)throws MessagingException, IOException{
        try{
            UserVO userDetail = userService.getUserByEmail(email.getEmail());

            if (!email.getEmail().equals(userDetail.getEmail())) throw new NotFoundException("Email not found");
            //이메일 토큰 만료 시간 5분
            String acToken = jwtTokenProvider.createEmailToken(userDetail.getEmail());
            HashMap<String, String> emailValues = new HashMap<>();
            emailValues.put("id", userDetail.getId());
            emailValues.put("accesskey", acToken);
            mailService.send("codefolio 비밀번호 찾기 메일입니다.", email.getEmail(), "changePwd", emailValues);
//        mailService.changePwd(mailTO, acToken);

            return ResponseEntity.ok(new JsonResponse(acToken,"success",200,"confirmMail"));
        }catch (MessagingException e){
            log.info("Message Exception");
            throw new GlobalException("Message Exception");
        }catch (IOException e){
            log.error("IOException");
            throw new GlobalException("IOException");
        }
    }


    @PostMapping("/sendInquiry")
    public Object sendInquiry(@RequestBody InquiryMailForm mail)throws MessagingException, IOException{
        HashMap<String, String> emailValues = new HashMap<>();
        emailValues.put("email", mail.getEmail());
        emailValues.put("message", mail.getMessage());
        mailService.send(mail.getEmail()+"님의 문의 메일입니다.", "codefolio19@gmail.com", "sendInq", emailValues);

        return ResponseEntity.ok(new JsonResponse(null,"success",200,"sendInquiry"));
    }

}

