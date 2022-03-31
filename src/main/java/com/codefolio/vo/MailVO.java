package com.codefolio.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailVO {
    private String email;
    private String title;
    private String message;
    private String rString;
}
