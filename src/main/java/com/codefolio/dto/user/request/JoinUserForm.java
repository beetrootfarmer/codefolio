package com.codefolio.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinUserForm {
    private String id;
    private String name;
    private String pwd;
    private String email;
    private String gitId;
}
