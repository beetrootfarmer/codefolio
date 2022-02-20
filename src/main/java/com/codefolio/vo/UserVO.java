package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private int userSeq;
    //user 기본정보
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String gitId;

    //user 생성일, 접속일
    private String creDate;
    private String recDate;

    //user 프로필, 소개글, 직업
    private String job;
    private String stack;
    private String img;
    private String introFile;

    //user 탈퇴유무(false => 탈퇴유저)
    private boolean reg;

}
