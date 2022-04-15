package com.codefolio.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JsonResponse {
    private int status;
    private String code;
    private String message;
    private Object data;

    public JsonResponse(Object data, String code,int status,String message) {

        this.status = status;
        this.code = code;
        this.message=message;
        this.data = data;
    }

}
