package com.codefolio.dto.proj.response;

import lombok.Getter;

@Getter
public class GetProjandFileResponse {
    Object proj;
    Object fileList;

    public GetProjandFileResponse(Object data1, Object data2){
        this.proj = data1;
        this.fileList = data2;
    }
}
