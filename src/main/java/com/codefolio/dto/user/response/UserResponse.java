package com.codefolio.dto.user.response;

import com.codefolio.vo.ProjVO;
import com.codefolio.vo.UserVO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class UserResponse {

    //user 기본정보
    private String id;
    private String name;
    private String email;
    private String gitId;

    //user 프로필, 소개글, 직업
    private String job;
    private String stack;
    private String img;
    private String introFile;


    public UserResponse(UserVO user){
        this.email = user.getEmail();
        this.gitId = user.getGitId();
        this.id = user.getId();
        this.img = user.getImg();
        this.introFile = user.getIntroFile();
        this.job = user.getJob();
        this.name = user.getName();
        this.stack = user.getStack();
    }


}
