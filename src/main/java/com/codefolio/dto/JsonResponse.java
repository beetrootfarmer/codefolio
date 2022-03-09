package com.codefolio.dto;


public class JsonResponse {
    private int status;
    private String ErrorCode;
    private String message;
    private UserResponse dataObject;

    public JsonResponse(int status,String ErrorCode,String message) {
        this.status = status;
        this.ErrorCode=ErrorCode;
        this.message=message;
    }

    public void setDataObject(UserResponse dataObject) {
        this.dataObject = dataObject;
    }
}
