package com.codefolio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
    private int fileSeq;
    private String boardType;
    private int boardSeq;
    private String fileName;
    private String fileDownloadUri;
    private long size;
}
