package com.codefolio.vo;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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

    //user 프로필, 소개글
    private String img;
    private String introFile;

    //user 탈퇴유무(false => 탈퇴유저)
    private boolean reg;

    // user임을 확인해주는 역할
    private String role;

    //소셜로그인 정보
    private String provider;
    private String providerId;

    public CharSequence getPassword() {
        return null;
    }

    public char[] getUserSeq() {
        return null;
    }



//     public UserVO ( String id, String pwd, String name, String email,
//                  String role, String provider, String providerId){
// //         super();
//         this.id = id;
// 		this.pwd = pwd;
// 		this.name = name;
// 		this.email = email;
// 		this.role = role;
// 		this.provider = provider;
// 		this.providerId = providerId;
//     }



}
