package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO{

    private int userSeq;
    private String role;    //ROLE-USER, ROLE-ADMIN
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

    //소셜 로그인과 일반 사용자 구분
    private boolean reg;

    private String refToken;

    private String provider;
    private String providerId;

//    @Builder
//    UserVO(String id){
//        this.id=id;
//    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
//        auth.add(new SimpleGrantedAuthority(AUTHORITY));
//        return auth;
//    }

}
