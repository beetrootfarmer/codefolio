package com.codefolio.controller;

import com.codefolio.dto.ErrorResponse;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.UserResponse;
import com.codefolio.service.FileService;
import com.codefolio.service.MailService;
import com.codefolio.service.UserService;
import com.codefolio.utils.FileUtils;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.MailTO;
import com.codefolio.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home(){
        return "home";
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody UserVO user){
//        String userName=userService.secLogin(user);
//
//        if(userName!=null){
//            return ResponseEntity.ok(userName+" 로그인 성공");
//        }else return ResponseEntity.notFound().build();
//
//    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Object> joinUser(@RequestBody UserVO user){
        user.setRole("ROLE_USER");
        String encUserPwd = bCryptPasswordEncoder.encode(user.getPwd());
        System.out.println("encodig PWD : \n"+encUserPwd);
        user.setPwd(encUserPwd);   //encoding된 password 넣기
        Integer userSeq = userService.joinUser(user);

        return getUser(user.getId());
    }


    //회원가입 email의 중복성 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody UserVO user){
        int result= userService.checkEmail(user.getEmail());
        if(result!=0)return ResponseEntity.badRequest().body("fail"+result);
        else return ResponseEntity.ok("success");
    }

    //회원가입 name의 중복성 체크
    @PostMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestBody UserVO user){
        int result = userService.checkId(user.getId());
        if(result!=0)return ResponseEntity.badRequest().body("fail");
        else return ResponseEntity.ok("success");
    }

//    //TODO: jwt로그인 방식 구현하기 필요
//    //회원 로그인
//    @PostMapping("/login")
//    public ResponseEntity<String> loginCheck(@RequestBody UserVO user){
//        String userId = userService.checkLogin(user);
//        if(userId!=null) return ResponseEntity.ok(userId+" 로그인 성공");
//        else return ResponseEntity.notFound().build();
//    }

    //TODO : response dto로 매핑해서 response하기(완료)
    //Get user(userName으로 유저 조회) => response Entity 사용
    @GetMapping("/detail/{userId}")
    @ResponseBody
    public ResponseEntity<Object> getUser(@PathVariable String userId){
        try {
            UserVO userDetail = userService.getUser(userId);
            System.out.println("=========getUser=======\n"+userDetail);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UserResponse(userDetail,200,"success",userDetail.getName()+"님의 정보 조회 완료"));
        } catch (Exception re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "NOT_FOUND","not found User"));
        }

    }


    //Get userlist
    @GetMapping("/list")
    public ResponseEntity<List<UserVO>> getAllUserData() {
        List<UserVO> userList =  userService.getAllUserData();
        return ResponseEntity.ok(userList);
    }

    //TODO : 마이바티스 동적 sql 좀더 알아보기 => null 값 저장 안되게 하기
    //Update user => 유저 정보를 jwt로 받아와 찾을 수 있게 만들자
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId,@RequestBody UserVO user){
        user.setId(userId);
        userService.updateUser(user);
//        UserResponse userDetail = getUser(userId);
        return getUser(userId);
    }

    //user Img upload => user
    @PostMapping("/{userId}/upload")
    public ResponseEntity<Object> insertUserImg(@PathVariable String userId, HttpServletRequest request, MultipartHttpServletRequest mhsr)throws Exception{
        UserVO user = userService.getUser(userId);
        int userSeq = user.getUserSeq();
        int fileSeq = fileService.getFileSeq();
        FileUtils fileUtils = new FileUtils();
        List<FileVO> fileList = fileUtils.parseFileInfo(userSeq,"user", request,mhsr);
        if(CollectionUtils.isEmpty(fileList) == false) {
            fileService.saveFile(fileList);
            user.setId(userId);
            user.setImg(fileList.get(0).getFileDownloadUri());
            userService.updateUserImg(user);
            System.out.println("saveFile()탐 + fileList===" + fileList);
        }else ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400,"BAD_REQUEST","file 저장 실패"));
        return getUser(user.getId());
    }


    //DeleteUser => id조회 => 등록된 회원만 삭제를 할 수 있다고 생각
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(userId+" 회원이 삭제되었습니다.");
    }

    //TODO: jwt 방식으로 보내기
    //email 인증 => userEmail, userId 입력
    @PostMapping("/mailConfirm")
    @ResponseBody
    public ResponseEntity<MailTO> mailConfirm(@RequestBody UserVO user) {

        String userEmail=user.getEmail();
        String userId = user.getId();

        MailTO mailTO = new MailTO(userId,userEmail);
        mailService.checkEmail(mailTO);

        return ResponseEntity.ok(mailTO);
    }

//    //TODO: jwt방식으로 비밀번호 변경 페이지 보내기
//    //email 조회 후 해당 유저 토큰과 함께 비밀번호 변경 url 보냄
//    public ResponseEntity<MailTO> confirmPwd(@RequestBody UserVO user){
//
//    }
}