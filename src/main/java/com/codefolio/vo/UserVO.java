package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private int userSeq;
    private String userId;
    private String userPwd;
    private String userName;
    private String userEmail;
    private String userGitId;

}
