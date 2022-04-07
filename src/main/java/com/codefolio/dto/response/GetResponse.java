package com.codefolio.dto.response;

import lombok.Getter;

@Getter
public class GetResponse {
    Object user;
    Object projList;

    public GetResponse(Object data1, Object data2){
        this.user = data1;
        this.projList = data2;
    }
}
