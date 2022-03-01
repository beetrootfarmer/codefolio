package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO{

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
    private int img;
    private String introFile;

    //소셜 로그인과 일반 사용자 구분
    private boolean reg;



//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
//        auth.add(new SimpleGrantedAuthority(AUTHORITY));
//        return auth;
//    }

}
