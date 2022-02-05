package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private int userSeq;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;
    private String userGitId;

}
