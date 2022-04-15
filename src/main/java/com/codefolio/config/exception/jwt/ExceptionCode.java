package com.codefolio.config.exception.jwt;

import lombok.Getter;

//jwt Filter Exception 관리
//https://bcp0109.tistory.com/303?category=981824
@Getter
public enum ExceptionCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "INVALID_INPUT_VALUE"),
    METHOD_NOT_ALLOWED(405, "C002", "METHOD_NOT_ALLOWED"),

    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    UNKNOWN_ERROR(400,"M002","UNKNOWN_ERROR"),
    WRONG_TYPE_TOKEN(400, "M002", "WRONG_TYPE_TOKEN"),
    EXPIRED_TOKEN(400, "M002", "EXPIRED_TOKEN"),
    UNSUPPORTED_TOKEN(400, "M002", "UNSUPPORTED_TOKEN"),
    ACCESS_DENIED(400, "M002", "ACCESS_DENIED"),
    PERMISSION_DENIED(400, "M002", "PERMISSION_DENIED"),
    WRONG_TOKEN(400, "M002", "WRONG_TOKEN")

    ;

    private final int status;
    private final String code;
    private final String message;

    ExceptionCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
