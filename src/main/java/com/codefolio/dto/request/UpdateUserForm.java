package com.codefolio.dto.request;

import lombok.Getter;

@Getter
public class UpdateUserForm {
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
}
