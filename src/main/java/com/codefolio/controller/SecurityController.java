package com.codefolio.controller;

import com.codefolio.config.exception.GlobalException;
import com.codefolio.config.exception.NotCreateException;
import com.codefolio.config.exception.NotFoundException;
import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.service.FileService;
import com.codefolio.service.UserService;
import com.codefolio.utils.FileUtils;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


//"/user/**" filtering중
//Security 인증이 필요한 메서드 모음
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class SecurityController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String mainhome(){
        return "mainhome";
    }



    //user Img upload => user
    @PostMapping("/{userId}/upload")
    public ResponseEntity<Object> insertUserImg(@PathVariable String userId, HttpServletRequest request, MultipartHttpServletRequest mhsr)throws Exception{
        log.info("insertUserImg");
        UserVO user = userService.getUserById(userId);
        int userSeq = user.getUserSeq();
        FileUtils fileUtils = new FileUtils();
        List<FileVO> fileList = fileUtils.parseFileInfo(userSeq,"user", request,mhsr);
        if(CollectionUtils.isEmpty(fileList) == false) {
            fileService.saveFile(fileList);
            user.setId(userId);
            user.setImg(fileList.get(0).getFileDownloadUri());
            userService.updateUserImg(user);
        }else throw new NotCreateException("Unable create file");
        UserVO userDetail = userService.getUserById(userId);
        return ResponseEntity.ok(new JsonResponse(userDetail,200,"insertUserImg"));
    }

    //DeleteUser => id조회 => 등록된 회원만 삭제를 할 수 있다고 생각
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(userId+" 회원이 삭제되었습니다.");
    }

    //TODO : 마이바티스 동적 sql 좀더 알아보기 => null 값 저장 안되게 하기
    //Update user => 유저 정보를 jwt로 받아와 찾을 수 있게 만들자
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId,@RequestBody UserVO user){
        log.info("updateUser");
        UserVO userVO = userService.getUserById(userId);

        if(user.getName()!=null) userVO.setName(user.getName());
        if(user.getImg()!=null) userVO.setImg(user.getImg());
        if(user.getPwd()!=null) userVO.setPwd(user.getPwd());
        if(user.getGitId()!=null)userVO.setGitId(user.getGitId());
        if(user.getStack()!=null)userVO.setStack(user.getStack());
        if(user.getIntroFile()!=null)userVO.setIntroFile(user.getIntroFile());
        if(user.getJob()!=null)userVO.setJob(user.getJob());
        userService.updateUser(user);
        if(user.getId()!=null)userService.updateUserId(userId,user.getId());


//        UserResponse userDetail = getUser(userId);
//        return getUser(userId);
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    //Get user(userName으로 유저 조회) => response Entity 사용
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> getUserProfile(@PathVariable String userId, HttpServletRequest request){
        log.info("===getUser===");
        //TODO : jwt parsing 해서 userEmail 받아오기
        String getAcToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserPk(getAcToken);
        UserVO getUserByEmail = userService.getUserByEmail(userEmail);

        try {
            if(getUserByEmail.getId().equals(userId))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JsonResponse(getUserByEmail,200,"getUserProfile"));
            else throw new NotFoundException("Invalid user id");
        } catch (Exception re) {
            throw new GlobalException("getUserProfile");
        }
    }

    @PostMapping("/changePwd")
    @ResponseBody
    public ResponseEntity<Object> changePwd(@RequestBody UserVO user,HttpServletRequest request){
        String getAcToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserPk(getAcToken);
        System.out.println("userEmail================="+userEmail);
        try{
            if(userEmail==null)throw new NotFoundException("Unable found User email");
            UserVO getUser = userService.getUserByEmail(userEmail);
            String saltkey = userService.getSecString();
            String encUserPwd = passwordEncoder.encode(user.getPwd()+saltkey);
            getUser.setSaltKey(saltkey);
            getUser.setPwd(encUserPwd);   //encoding된 password 넣기
            userService.updateUser(getUser);
            UserVO userDetail = userService.getUserByEmail(userEmail);
            return ResponseEntity.ok(new JsonResponse(userDetail,200,"changePwd"));
        }catch (Exception e){
            throw new NotCreateException("not create user password");
        }
    }

}
