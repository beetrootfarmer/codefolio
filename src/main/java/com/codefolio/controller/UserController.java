package com.codefolio.controller;

import com.codefolio.config.exception.controller.BadRequestException;
import com.codefolio.config.exception.controller.GlobalException;
import com.codefolio.config.exception.controller.MethodNotAllowedException;
import com.codefolio.config.exception.controller.NotFoundException;
import com.codefolio.config.jwt.JwtService;
import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.user.request.*;
import com.codefolio.dto.user.response.GetUserResponse;
import com.codefolio.dto.user.response.TokenResponse;
import com.codefolio.dto.user.response.UserResponse;
import com.codefolio.service.MailService;
import com.codefolio.service.ProjService;
import com.codefolio.service.TokenService;
import com.codefolio.service.UserService;
import com.codefolio.utils.UuidUtil;
import com.codefolio.vo.ProjVO;
import com.codefolio.vo.TokenVO;
import com.codefolio.vo.UserVO;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    private final JwtService jwtService;
    private final TokenService tokenService;

//    @Operation(summary = "test hello", description = "hello api example")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK !!"),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
//    })


    @GetMapping("")
    public String hello(){
        return "Hello! We are Codefolio!!";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<Object> joinUser(@RequestBody JoinUserForm joinForm){
        try{
            log.info("[API] JoinUser API");
            UserVO user = UserVO.builder().id(joinForm.getId()).email(joinForm.getEmail()).name(joinForm.getName()).gitId(joinForm.getGitId()).build();
            user.setUUID(UuidUtil.generateType1UUID());
            String saltkey = userService.getSecString();
            String encUserPwd = passwordEncoder.encode(joinForm.getPwd()+saltkey);
            user.setSaltKey(saltkey);
            user.setPwd(encUserPwd);   //encoding된 password 넣기
            userService.joinUser(user);
            TokenVO tokenVO = TokenVO.builder().UUID(user.getUUID()).build();
            tokenService.joinUUID(tokenVO);
            return ResponseEntity.ok(new JsonResponse(null,"success",200,"joinUser"));
        }catch (NullPointerException e){
            log.error("[ERROR]NullPointerException / join User");
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            log.error("[ERROR]Exception / join User");
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Object loginUser(@RequestBody LoginUserForm loginForm){
        try{
            log.info("[API] LoginUser API");
            UserVO userDetail = userService.getUserByEmail(loginForm.getEmail());
            if(!userDetail.getEmail().equals(loginForm.getEmail())) return new NotFoundException("unsigned email");
            if(!passwordEncoder.matches(loginForm.getPwd()+userService.getSaltKey(loginForm.getEmail()),userDetail.getPwd())){
                log.error("[ERROR] Wrong Password");
                throw new NotFoundException("password not match");
            }
            //login성공시 actoken과 reftoken 재발행
            String newAcToken = jwtService.createAcToken(userDetail.getUUID());
            if(newAcToken==null) return new MethodNotAllowedException("Unable to create token.");
            String newRefToken = jwtService.createRefToken(userDetail.getUUID());
            TokenResponse tokenResponse = TokenResponse.builder().token(newAcToken).regDate(jwtService.refDate()).build();
            return ResponseEntity.ok(new JsonResponse(tokenResponse,"success",200,"loginUser"));
        }catch(NullPointerException e) {
            log.error("[ERROR]NullPointerException / login User");
            throw new NotFoundException("not found user");
        }catch (Exception e){
            log.error("[ERROR]Exception / login User");
            throw new GlobalException(e.getMessage());
        }
    }


    @ApiImplicitParams({@ApiImplicitParam(name="X-AUTH-TOKEN",value = "HttpServletRequest", required = true, dataType = "string",paramType = "header")})
    @GetMapping("/auth/refToken")
    private ResponseEntity<Object> checkRefToken(HttpServletRequest request){
        log.info("[API] checkRefToken");
        try{
            String getAcToken = jwtService.resolveRefToken(request);
            if(getAcToken==null)throw new BadRequestException("no AcToken");
            TokenVO tokenVO = tokenService.getTokenByAcToken(getAcToken);
            if(tokenVO==null)throw new BadRequestException("Bad Approach refToken");
            if(jwtService.validateRefToken(tokenVO.getRefToken()))throw new BadRequestException("refToken expired");
            String newAcToken = jwtService.createAcToken(tokenVO.getUUID());
            TokenResponse tokenForm = TokenResponse.builder().token(newAcToken).regDate(jwtService.refDate()).build();
            return ResponseEntity.ok(new JsonResponse(tokenForm,"OK",200,"checkRefToken"));
        }catch(NullPointerException e){
            throw new NotFoundException(e.getMessage());
        }
        catch (ExpiredJwtException e){
            log.info("[ERROR] checkRefToken : Expired");
            throw new GlobalException(e.getMessage());
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
            log.info("GetUser API");
            UserVO user = userService.getUserById(userId);
            List<ProjVO> proj = projService.getProjByUser(userId);
            UserResponse userDetail = new UserResponse(user);
            GetUserResponse data = new GetUserResponse(userDetail, proj);
            return ResponseEntity.ok(new JsonResponse(data,"success",200,"getUser"));
        }catch (NullPointerException e){
            log.error("[ERROR]NullPointerException / getUser");
            throw new NotFoundException("not found User");
        }catch (Exception e){
            log.error("[ERROR]Exception / getUser");
            throw new GlobalException("global Exception");
        }
    }


    @PostMapping("/mailConfirm")
    @ResponseBody
    public ResponseEntity<Object> confirmMail(@RequestBody ToEmailForm email){
        try{
            log.info("ConfirmMil API");
            String seckey = userService.getSecString();
            HashMap<String, String> emailValues = new HashMap<>();
            emailValues.put("seckey",seckey);
            mailService.send("codefolio에서 전송한 이메일입니다. ",email.getEmail(), "emailConfirm", emailValues);
            return ResponseEntity.ok(new JsonResponse(seckey,"success",200,"confirmMail"));
        }catch (MessagingException e){
            log.error("[ERROR]NullPointerException / confirmMail");
            throw new GlobalException("Message Exception");
        }catch (IOException e){
            log.error("[ERROR]Exception / confirmMail");
            throw new GlobalException("IOException");
        }
    }

    @PostMapping("/mailToPwd")
    @ResponseBody
    public ResponseEntity<Object> mailToPwd(@RequestBody ToEmailForm email){
        try{
            log.info("MailToPwd API");
            UserVO userDetail = userService.getUserByEmail(email.getEmail());
            if (!email.getEmail().equals(userDetail.getEmail())) throw new NotFoundException("Email not found");
            //이메일 토큰 만료 시간 5분
            String acToken = jwtTokenProvider.createEmailToken(userDetail.getEmail());
            HashMap<String, String> emailValues = new HashMap<>();
            emailValues.put("id", userDetail.getId());
            emailValues.put("accesskey", acToken);
            mailService.send("codefolio 비밀번호 찾기 메일입니다.", email.getEmail(), "changePwd", emailValues);
            return ResponseEntity.ok(new JsonResponse(acToken,"success",200,"confirmMail"));
        }catch (MessagingException e){
            log.error("[ERROR]MessagingException / mailToPwd");
            throw new GlobalException("Message Exception");
        }catch (IOException e){
            log.error("[ERROR]IOException / mailToPwd");
            throw new GlobalException("IOException");
        }
    }


    @PostMapping("/sendInquiry")
    public Object sendInquiry(@RequestBody InquiryMailForm mail){
        try{
            log.info("SendInquiry API");
            HashMap<String, String> emailValues = new HashMap<>();
            emailValues.put("email", mail.getEmail());
            emailValues.put("message", mail.getMessage());
            mailService.send(mail.getEmail()+"님의 문의 메일입니다.", "codefolio19@gmail.com", "sendInq", emailValues);
            return ResponseEntity.ok(new JsonResponse(null,"success",200,"sendInquiry"));
        }catch (MessagingException e){
            log.error("[ERROR]MessagingException / mailToPwd");
            throw new GlobalException("Message Exception");
        }catch (IOException e){
            log.error("[ERROR]IOException / mailToPwd");
            throw new GlobalException("IOException");
        }
    }

}

