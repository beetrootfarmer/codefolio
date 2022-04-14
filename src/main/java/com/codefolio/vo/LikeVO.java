package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeVO {
    private int likeSeq;
    private int projSeq;
    private String userId;
}