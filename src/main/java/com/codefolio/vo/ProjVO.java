package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjVO {
    private int projSeq;
    private String projUser;
    private String projTitle;
    private String projGitId;
    private int projLike;
    private int projView;
    private String projStack;



}
