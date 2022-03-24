package com.codefolio.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JsonResponse {
    private int status;
    private String message;
    private Object data;

    public JsonResponse(Object data,int status,String message) {
        this.status = status;
        this.message=message;
        this.data = data;
    }

}
