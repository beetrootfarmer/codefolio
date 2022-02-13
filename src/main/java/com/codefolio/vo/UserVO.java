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
    private String userId;
    private String userPwd;
    private String userName;
    private String userEmail;
    private String userGitId;

    //user 생성일, 접속일
    private String userCreDate;
    private String userRecDate;

    //user 프로필, 소개글
    private String userImg;
    private String userIntroFile;

    //user 탈퇴유무(false => 탈퇴유저)
    private boolean userReg;

}
