package com.codefolio.dto;

import com.codefolio.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse{
    private int status;

    //user 기본정보
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String gitId;

    //user 프로필, 소개글, 직업
    private String job;
    private String stack;
    private int img;
    private String introFile;

    public UserResponse(UserVO user,int status){
        this.status=status;
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
