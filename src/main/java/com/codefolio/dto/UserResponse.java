package com.codefolio.dto;

import com.codefolio.vo.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse{
    private int status;
    private String ErrorCode;
    private String message;

    //user 기본정보
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String gitId;

    //user 프로필, 소개글, 직업
    private String job;
    private String stack;
    private String img;
    private String introFile;

    private UserVO user;

    public UserResponse(UserVO user, int status, String ErrorCode, String message){
        this.status=status;
//        this.user=user;
        this.ErrorCode=ErrorCode;
        this.message=message;
        this.id=user.getId();
        this.pwd=user.getPwd();
        this.name=user.getName();
        this.email=user.getEmail();
        this.gitId=user.getGitId();
        this.job=user.getJob();
        this.stack=user.getStack();
        this.img=user.getImg();
        this.introFile=user.getIntroFile();

    }



}
