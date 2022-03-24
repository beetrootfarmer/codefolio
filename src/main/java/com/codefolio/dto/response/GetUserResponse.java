package com.codefolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

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
