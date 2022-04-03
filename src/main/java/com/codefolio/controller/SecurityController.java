package com.codefolio.controller;

import com.codefolio.config.exception.GlobalException;
import com.codefolio.config.exception.NotCreateException;
import com.codefolio.config.exception.NotFoundException;
import com.codefolio.config.exception.TestException;
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
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<Object> insertUserImg(HttpServletRequest request, MultipartHttpServletRequest mhsr)throws Exception{
        log.info("insertUserImg");
        String userUUID = getUUID(request);
        UserVO user = userService.getUserByUUID(userUUID);
        int userSeq = user.getUserSeq();
        FileUtils fileUtils = new FileUtils();
        List<FileVO> fileList = fileUtils.parseFileInfo(userSeq,"user", request,mhsr);
        if(CollectionUtils.isEmpty(fileList) == false) {
            fileService.saveFile(fileList);
            user.setImg(fileList.get(0).getFileDownloadUri());
            userService.updateUserImg(user);
        }else throw new NotCreateException("Unable create file");
        UserVO userDetail = userService.getUserByUUID(userUUID);
        return ResponseEntity.ok(new JsonResponse(userDetail,200,"insertUserImg"));
    }

    //DeleteUser => id조회 => 등록된 회원만 삭제를 할 수 있다고 생각
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId, HttpServletRequest request){
        String UUID = getUUID(request);
        userService.deleteUser(UUID);
        return ResponseEntity.ok(userId+" 회원이 삭제되었습니다.");
    }

    //Update user => 유저 정보를 jwt로 받아와 찾을 수 있게 만들자
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId,@RequestBody UserVO user, HttpServletRequest request){
        log.info("updateUser");
        String userUUID = getUUID(request);

        UserVO userVO = userService.getUserByUUID(userUUID);
        if(!userId.equals(userVO.getId())) throw new NotFoundException("not found user");

        if(user.getName()!=null) userVO.setName(user.getName());
        if(user.getImg()!=null) userVO.setImg(user.getImg());
        if(user.getPwd()!=null) userVO.setPwd(user.getPwd());
        if(user.getGitId()!=null)userVO.setGitId(user.getGitId());
        if(user.getStack()!=null){
            userVO.setStack(user.getStack());
        }
        if(user.getIntroFile()!=null)userVO.setIntroFile(user.getIntroFile());
        if(user.getJob()!=null)userVO.setJob(user.getJob());
        userService.updateUser(user);
        if(user.getId()!=null)userService.updateUserId(userUUID,user.getId());

        UserVO userDetail = userService.getUserByUUID(userUUID);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new JsonResponse(userDetail,200,"getUserProfile"));
//        UserResponse userDetail = getUser(userId);
//        return getUser(userId);
    }

    //Get user(userName으로 유저 조회) => response Entity 사용
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> getUserProfile(HttpServletRequest request){
        log.info("===getUser===");
        try{
            String userUUID = getUUID(request);
            UserVO getUserByUUID = userService.getUserByUUID(userUUID);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new JsonResponse(getUserByUUID,200,"getUserProfile"));
        }catch (Exception e){
            throw new NotFoundException("user not found");
        }
    }

    @PostMapping("/changePwd")
    @ResponseBody
    public ResponseEntity<Object> changePwd(@RequestBody UserVO user,HttpServletRequest request){
        String userUUID = getUUID(request);
        System.out.println("userUUID================="+userUUID);
        try{
//            if(userUUID==null)throw new NotFoundException("Unable found User email");
            UserVO getUser = userService.getUserByUUID(userUUID);
            String saltkey = userService.getSecString();
            String encUserPwd = passwordEncoder.encode(user.getPwd()+saltkey);
            getUser.setSaltKey(saltkey);
            getUser.setPwd(encUserPwd);   //encoding된 password 넣기
            userService.updatePwd(getUser);
            UserVO userDetail = userService.getUserByUUID(userUUID);
            return ResponseEntity.ok(new JsonResponse(userDetail,200,"changePwd"));
        }catch (Exception e){
            throw new NotCreateException("not create user password");
        }
    }

//    private Object checkAcToken(String userUUID,HttpServletRequest request){
//        try{
//            String getAcToken = jwtTokenProvider.resolveToken(request);
//            String getUserUUID = jwtTokenProvider.getUserPk(getAcToken);
////            if(!userUUID.equals(getUserUUID)) return new NotFoundException("UUID가 일치하지 않음");
//            return getUserUUID;
//        }catch(Exception e){
//            return new GlobalException("token 유효하지 않음");
//        }
//    }
    private String getUUID(HttpServletRequest request){
        try{
            String getAcToken = jwtTokenProvider.resolveToken(request);
            return jwtTokenProvider.getUserPk(getAcToken);
        }catch (Exception e){
            throw new TestException(e);
        }
    }


}
