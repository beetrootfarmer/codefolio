package com.codefolio.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {
    private int tokenSeq;
    private String acToken;
    private String refToken;
    private String UUID;
    private String regDate;
}
