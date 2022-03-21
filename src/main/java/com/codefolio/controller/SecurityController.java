package com.codefolio.controller;

import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.ErrorResponse;
import com.codefolio.dto.UserResponse;
import com.codefolio.service.FileService;
import com.codefolio.service.UserService;
import com.codefolio.utils.FileUtils;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


//"/user/**" filtering중
//Security 인증이 필요한 메서드 모음
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class SecurityController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/")
    public String mainhome(){
        return "mainhome";
    }



    //user Img upload => user
    @PostMapping("/{userId}/upload")
    public ResponseEntity<Object> insertUserImg(@PathVariable String userId, HttpServletRequest request, MultipartHttpServletRequest mhsr)throws Exception{
        UserVO user = userService.getUserById(userId);
        int userSeq = user.getUserSeq();
//        int fileSeq = fileService.getFileSeq();
        FileUtils fileUtils = new FileUtils();
        List<FileVO> fileList = fileUtils.parseFileInfo(userSeq,"user", request,mhsr);
        if(CollectionUtils.isEmpty(fileList) == false) {
            fileService.saveFile(fileList);
            user.setId(userId);
            user.setImg(fileList.get(0).getFileDownloadUri());
            userService.updateUserImg(user);
        }else ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400,"BAD_REQUEST","file 저장 실패"));
        return ResponseEntity.ok(userService.getUserById(user.getId()));
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
        user.setId(userId);
        userService.updateUser(user);
//        UserResponse userDetail = getUser(userId);
//        return getUser(userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    //Get user(userName으로 유저 조회) => response Entity 사용
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> getUser(@PathVariable String userId, HttpServletRequest request){
        //TODO : jwt parsing 해서 userEmail 받아오기
        UserVO getUserById = userService.getUserById(userId);
        String getAcToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserPk(getAcToken);
        UserVO getUserByEmail = userService.getUser(userEmail);

        try {
            if(getUserByEmail.getId().equals(getUserById.getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new UserResponse(getUserByEmail,200,"success",getUserByEmail.getName()+"님의 정보 조회 완료"));
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404,"User invalid","유저 토큰과 정보가 맞지 않습니다. "));
        } catch (Exception re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "NOT_FOUND","not found User"));
        }
    }

}
