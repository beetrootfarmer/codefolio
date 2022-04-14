//package com.codefolio.controller;
//
//import com.codefolio.config.exception.NotCreateException;
//import com.codefolio.config.exception.NotFoundException;
//import com.codefolio.config.exception.TestException;
//import com.codefolio.config.jwt.JwtTokenProvider;
//import com.codefolio.dto.JsonResponse;
//import com.codefolio.dto.response.GetResponse;
//import com.codefolio.dto.response.UserResponse;
//import com.codefolio.service.MailService;
//import com.codefolio.service.ProjService;
//import com.codefolio.service.UserService;
//import com.codefolio.utils.UuidUtil;
//import com.codefolio.vo.MailTO;
//import com.codefolio.vo.MailVO;
//import com.codefolio.vo.ProjVO;
//import com.codefolio.vo.UserVO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.mail.MessagingException;
//import javax.servlet.ServletRequest;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class UserController {
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserService userService;
//
//    private final MailService mailService;
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    private final ProjService projService;
//
////    @GetMapping("/")
////    public String validateUser(ServletRequest request){
////        String getActoken = jwtTokenProvider.resolveToken((HttpServletRequest)request);
////        String userEmail = jwtTokenProvider.
////
////        //유효한 토큰인지 확인합니다.
//////        case1: access token과 refresh token 모두가 만료된 경우 -> 에러 발생
//////        case2: access token은 만료됐지만, refresh token은 유효한 경우 ->  access token 재발급
//////        case3: access token은 유효하지만, refresh token은 만료된 경우 ->  refresh token 재발급
//////        case4: accesss token과 refresh token 모두가 유효한 경우 -> 다음 미들웨어로
////
////        if (getActoken != null && jwtTokenProvider.validateToken(getActoken)) {
////            //accessToken 유효함
////
////            String getReftoken= userDetail.getRefToken();
////            if(getReftoken!=null&&jwtTokenProvider.validateToken(getReftoken))   //refresh token유효함
////            {
////                //ac/ref 유효함
////                String newAcToken = jwtTokenProvider.createToken(userDetail.getEmail());
////                if(newAcToken==null) return new NotCreateException("Unable to create token.");
////                String newRefToken = jwtTokenProvider.createRefToken(userDetail.getEmail());
////                return newAcToken;
////            }
////            else
////                System.out.println("ref 만료");
////        }
////        else{
////            //accessToken 만료
////            if(getReftoken!=null&&jwtTokenProvider.validateToken(getReftoken))   //refresh token유효함
////                System.out.println("refToken 유효");
////            else System.out.println("ac/ref 만료");
////        }
////    }
//
//    @PostMapping("/join")
//    @ResponseBody
//    public ResponseEntity<Object> joinUser(@RequestBody UserVO user)throws Exception{
//        log.info("====join===");
//        user.setUUID(UuidUtil.generateType1UUID());
//        String saltkey = userService.getSecString();
//        String encUserPwd = passwordEncoder.encode(user.getPwd()+saltkey);
//        user.setSaltKey(saltkey);
//        user.setPwd(encUserPwd);   //encoding된 password 넣기
//        userService.joinUser(user);
//        UserVO userDetail = userService.getUserByEmail(user.getEmail());
//        return ResponseEntity.ok(new JsonResponse(userDetail,200,"joinUser"));
//    }
//
//    @PostMapping("/login")
//    @ResponseBody
//    public Object loginUser(@RequestBody UserVO user, ServletRequest request){
//        try{
//            log.info("[info]===login===");
//            UserVO userDetail = userService.getUserByEmail(user.getEmail());
//            System.out.println(userDetail);
//            if(!userDetail.getEmail().equals(user.getEmail())) return new NotFoundException("unsigned email");
//            if(!passwordEncoder.matches(user.getPwd()+userService.getSaltKey(user.getEmail()),userDetail.getPwd())){
//                log.error("[ERROR] Wrong Password");
//                throw new NotFoundException("password not match");
//            }
//            //login성공시 actoken과 reftoken 재발행
//            String newAcToken = jwtTokenProvider.createToken(userDetail.getUUID());
//            if(newAcToken==null) return new NotCreateException("Unable to create token.");
//            String newRefToken = jwtTokenProvider.createRefToken(userDetail.getUUID());
//            return newAcToken;
//        }catch(NullPointerException e) {
//            throw new TestException(e);
//        }
//
//    }
//
//
//    //회원가입 email의 중복성 체크
//    @PostMapping("/checkEmail")
//    public ResponseEntity<String> checkEmail(@RequestBody UserVO user){
//        int result= userService.checkEmail(user.getEmail());
//        if(result!=0)return ResponseEntity.badRequest().body("fail"+result);
//        else return ResponseEntity.ok("success");
//    }
//
//    //회원가입 name의 중복성 체크
//    @PostMapping("/checkId")
//    public ResponseEntity<String> checkId(@RequestBody UserVO user){
//        int result = userService.checkId(user.getId());
//        if(result!=0)return ResponseEntity.badRequest().body("fail");
//        else return ResponseEntity.ok("success");
//    }
//
//    //Get userlist
//    @GetMapping("/admin/list")
//    public ResponseEntity<List<UserVO>> getAllUserData() {
//        List<UserVO> userList =  userService.getAllUserData();
//        return ResponseEntity.ok(userList);
//    }
//
//
//        //TODO : user별 project list=> total proj view
//        //TODO : user별 follow list
//        // TODO : user별 좋아한 프로젝트
//    //getUser의 경우 유저에 따라 다르게 보여줘야하는 정보가 따로 없어 하나의 api로 만들었습니다.
//    @GetMapping("/{userId}")
//    @ResponseBody
//    public ResponseEntity<Object> getUser(@PathVariable String userId,HttpServletRequest request) {
//        String acToken = jwtTokenProvider.resolveToken(request);
//        if (jwtTokenProvider.validateToken(acToken))
//            return ResponseEntity.ok("codefolio/user/" + userId);
//
//        UserVO user = userService.getUserById(userId);
//        if(user==null)throw new NotFoundException("not found User");
//        List<ProjVO> proj = projService.getProjByUser(userId);
//
//        //TODO : UserMapping을 하고 싶은데 ㅠㅠㅠ 좀 더 해보자!!
//        UserResponse userDetail = new UserResponse(user);
//        GetResponse data = new GetResponse(userDetail, proj);
//        return ResponseEntity.ok(new JsonResponse(data, 200, "getUser"));
//    }
//
//    //TODO : (test)mail 100개 보내는데 4분정도 이후에 update 필요함
//    @PostMapping("/mailConfirm")
//    @ResponseBody
//    public ResponseEntity<String> confirmMail(@RequestBody UserVO user)throws MessagingException, IOException {
//        String userEmail=user.getEmail();
//        String userId = user.getId();
//        String seckey = userService.getSecString();
//
//        MailTO mailTO = new MailTO(userId,userEmail);
//        mailTO.setrString(seckey);
//
//        HashMap<String, String> emailValues = new HashMap<>();
//        emailValues.put("id", user.getId());
//        emailValues.put("seckey",seckey);
//        mailService.send("codefolio에서 전송한 이메일입니다. ", user.getEmail(), "emailConfirm", emailValues);
//
//        return ResponseEntity.ok(mailTO.getRString());
//    }
//
//
//    @PostMapping("/mailToPwd")
//    @ResponseBody
//    public Object mailToPwd(@RequestBody UserVO user)throws MessagingException, IOException{
//        UserVO userDetail = userService.getUserByEmail(user.getEmail());
//
//        if (!user.getEmail().equals(userDetail.getEmail())) throw new NotFoundException("Email not found");
//
//        MailTO mailTO = new MailTO(userDetail.getId(), userDetail.getEmail());
//        //이메일 토큰 만료 시간 5분
//        String acToken = jwtTokenProvider.createEmailToken(userDetail.getEmail());
//        HashMap<String, String> emailValues = new HashMap<>();
//        emailValues.put("id", userDetail.getId());
//        emailValues.put("accesskey", acToken);
//        mailService.send("codefolio 비밀번호 찾기 메일입니다.", user.getEmail(), "changePwd", emailValues);
////        mailService.changePwd(mailTO, acToken);
//        mailTO.setrString(acToken);
//
//        return ResponseEntity.ok(mailTO);
//    }
//
//
//    @PostMapping("/sendInquiry")
//    public Object sendInquiry(@RequestBody MailVO mail)throws MessagingException, IOException{
//        HashMap<String, String> emailValues = new HashMap<>();
//        emailValues.put("email", mail.getEmail());
//        emailValues.put("message", mail.getMessage());
//        mailService.send(mail.getEmail()+"님의 문의 메일입니다.", "codefolio19@gmail.com", "sendInq", emailValues);
//
//        return ResponseEntity.ok(mail);
//    }
//
//}
//
