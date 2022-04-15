package com.codefolio.dto.response;

import lombok.Getter;

@Getter
public class GetUserandProjSeqResponse {
    String userId;
    int projSeq;

    public GetUserandProjSeqResponse(String data1, int data2){
        this.userId = data1;
        this.projSeq = data2;
    }
}
