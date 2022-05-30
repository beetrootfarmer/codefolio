package com.codefolio.dto.user.response;

import lombok.Getter;

@Getter
public class GetUserResponse {
    Object user;
    Object proj;

    public GetUserResponse(Object data1, Object data2){
        this.user = data1;
        this.proj = data2;
    }
}
