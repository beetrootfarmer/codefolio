package com.codefolio.vo;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjVO {
    private int projSeq;
    private String user;
    private String title;
    private String url;
    private int view;
    private String stack;
    private String content;
    private Timestamp regDate;
    private String period;
    private String preview;

    private String keyword;
    private String keywordType;
    
    private int likes;
}
