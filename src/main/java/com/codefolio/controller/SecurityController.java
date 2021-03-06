package com.codefolio.controller;

import com.codefolio.config.exception.controller.BadRequestException;
import com.codefolio.config.exception.controller.GlobalException;
import com.codefolio.config.exception.controller.MethodNotAllowedException;
import com.codefolio.config.exception.controller.NotFoundException;
import com.codefolio.config.jwt.JwtTokenProvider;
import com.codefolio.dto.JsonResponse;
import com.codefolio.dto.user.request.ToPwdForm;
import com.codefolio.dto.user.request.UpdateUserForm;
import com.codefolio.dto.user.response.GetFollowList;
import com.codefolio.service.FileService;
import com.codefolio.service.FollowService;
import com.codefolio.service.UserService;
import com.codefolio.utils.FileUtils;
import com.codefolio.vo.FileVO;
import com.codefolio.vo.FollowVO;
import com.codefolio.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


//"/user/**" filtering중
//Security 인증이 필요한 메서드 모음
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final FollowService followService;

    //user Img upload => user
     @ApiImplicitParams({@ApiImplicitParam(name="X-AUTH-TOKEN",value = "HttpServletRequest", required = true, dataType = "string",paramType = "header"),
            @ApiImplicitParam(name="mhsr",value = "MultipartHttpServletRequest", required = true, dataType = "string",paramType = "header")})
    @PostMapping("/{userId}/upload")
    public ResponseEntity<Object> insertUserImg(@ApiIgnore HttpServletRequest request,@ApiIgnore @RequestParam(required = false) MultipartFile[] mhsr)throws Exception{
        log.info("insertUserImg");
        String userUUID = getUUID(request);
        UserVO user = userService.getUserByUUID(userUUID);
        int userSeq = user.getUserSeq();
        FileUtils fileUtils = new FileUtils();
        List<FileVO> fileList = fileUtils.parseFileInfo(userSeq,"user", mhsr);
        if(CollectionUtils.isEmpty(fileList) == false) {
            fileService.saveFile(fileList);
            user.setImg(fileList.get(0).getFileDownloadUri());
            userService.updateUserImg(user);
        }else throw new MethodNotAllowedException("Unable create file");
        return ResponseEntity.ok(new JsonResponse(null,"success",200,"insertUserImg"));
    }



    //DeleteUser => id조회 => 등록된 회원만 삭제를 할 수 있다고 생각
    @ApiImplicitParams({@ApiImplicitParam(name="X-AUTH-TOKEN",value = "HttpServletRequest", required = true, dataType = "string",paramType = "header")})
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@ApiIgnore HttpServletRequest request){
        String userUUID = getUUID(request);

        try{
            userService.deleteUser(userUUID);
            return ResponseEntity.ok(new JsonResponse(null,"success",200,"deleteUser"));
        }catch (NullPointerException e){
            log.error("updateUser : "+userUUID);
            throw new NotFoundException("not found User");
        }catch (Exception e){
            log.error("updateUser : "+userUUID);
            throw new GlobalException("global Exception");
        }

    }

    //Update user => 유저 정보를 jwt로 받아와 찾을 수 있게 만들자
    @ApiImplicitParams({@ApiImplicitParam(name="X-AUTH-TOKEN",value = "HttpServletRequest", required = true, dataType = "string",paramType = "header")})
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody UpdateUserForm user,@ApiIgnore HttpServletRequest request){
        log.info("UpdateUser API");
        String userUUID = getUUID(request);
        try{
            UserVO userVO = userService.getUserByUUID(userUUID);
            if(!userId.equals(userId)) throw new NotFoundException("not found user");
            if(user.getId()!=null) userVO.setId(user.getId());
            if(user.getName()!=null) userVO.setName(user.getName());
            if(user.getImg()!=null) userVO.setImg(user.getImg());
            if(user.getGitId()!=null)userVO.setGitId(user.getGitId());
            if(user.getStack()!=null) userVO.setStack(user.getStack());
            if(user.getIntroFile()!=null)userVO.setIntroFile(user.getIntroFile());
            if(user.getJob()!=null)userVO.setJob(user.getJob());
            userService.updateUser(userVO);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new JsonResponse(null,"success",200,"updateUser"));
        }catch (NullPointerException e){
            log.error("updateUser : "+userUUID);
            throw new NotFoundException("not found User");
        }catch (Exception e){
            log.error("updateUser : "+userUUID);
            throw new GlobalException("global Exception");
        }

    }

    @ApiImplicitParams({@ApiImplicitParam(name="X-AUTH-TOKEN",value = "HttpServletRequest", required = true, dataType = "string",paramType = "header")})
    @PostMapping("/changePwd")
    @ResponseBody
    public ResponseEntity<Object> changePwd(@RequestBody ToPwdForm pwd, @ApiIgnore HttpServletRequest request){
        String userUUID = getUUID(request);
        try{
            UserVO getUser = userService.getUserByUUID(userUUID);
            String saltkey = userService.getSecString();
            String encUserPwd = passwordEncoder.encode(pwd.getPwd()+saltkey);
            getUser.setSaltKey(saltkey);
            getUser.setPwd(encUserPwd);   //encoding된 password 넣기
            userService.updatePwd(getUser);
            return ResponseEntity.ok(new JsonResponse(null,"success",200,"changePwd"));
        }catch (NullPointerException e){
            throw new NotFoundException("not found User");
        }
        catch (Exception e){
            throw new GlobalException("global Exception");
        }
    }


    //follower : following 당하는 사람
    //follower <= following
//    @ApiImplicitParams({@ApiImplicitParam(name="X-AUTH-TOKEN",value = "HttpServletRequest", required = true, dataType = "string",paramType = "header")})
    @PostMapping("/{userId}/{followerId}/follow")
    public ResponseEntity followUser(@PathVariable String followerId, HttpServletRequest request){
        String followeeUUID = getUUID(request);
        UserVO follower = userService.getUserById(followerId);
        FollowVO followVO=FollowVO.builder().followerUUID(follower.getUUID()).followeeUUID(followeeUUID).build();
        try{
            int checkFollow = followService.checkFollow(followVO);
            log.error("[followUser] 이미 있는 사용자 입니다. ");
            throw new BadRequestException("이미 있는 사용자 입니다.");
        }catch (BindingException e){
            int followSeq = followService.followUser(followVO);
            return ResponseEntity.ok(new JsonResponse(null,"success",200,"followUser"));
        }
    }

    @DeleteMapping("/{userId}/{followerId}/follow")
    public ResponseEntity UnFollowUser(@PathVariable String followerId, HttpServletRequest request){
         String followeeUUID = getUUID(request);
         UserVO follower = userService.getUserById(followerId);
         FollowVO followVO = FollowVO.builder().followeeUUID(followeeUUID).followerUUID(follower.getUUID()).build();
         try{
             followService.UnFollowUser(followVO);
         }catch(NotFoundException e){
             throw new NotFoundException("user not found");
         }
        return ResponseEntity.ok(new JsonResponse(null,"success",200,"UnFollowUser"));
    }
//
    @GetMapping("/{userId}/follow")
    public ResponseEntity getFollowUserList(HttpServletRequest request){
         String userUUID = getUUID(request);
         List<GetFollowList> getFollowList = followService.getFollowUserList(userUUID);
         System.out.println(getFollowList);
         return ResponseEntity.ok(getFollowList);
    }


    private String getUUID(HttpServletRequest request){
        try{
            String getAcToken = jwtTokenProvider.resolveToken(request);
            return jwtTokenProvider.getUserPk(getAcToken);
        }catch (NullPointerException e){
            throw new NotFoundException("not Resolve AcToken");

        } catch (Exception e){
            throw new GlobalException("global Exception");
        }
    }


}
