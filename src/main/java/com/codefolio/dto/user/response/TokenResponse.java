package com.codefolio.dto.user.response;

import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
@Getter
public class TokenResponse {
    private String UUID;
    private String token;
    private String regDate;
}
