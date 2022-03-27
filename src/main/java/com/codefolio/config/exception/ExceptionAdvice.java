package com.codefolio.config.exception;

import com.codefolio.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    //404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundException(NotFoundException e){
//        String msg = "찾을 수 없음";
        String msg = e.getMessage();
        int status = 404;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(status,"NOT_FOUND",msg));
    }

    @ExceptionHandler(NotCreateException.class)
    public ResponseEntity<Object> NotCreateException(NotCreateException e){
        String msg = e.getMessage();
        int status = 404;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(status,"NOT_FOUND",msg));
    }

    //501
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> globalException(GlobalException e){
        String msg = e.getMessage();
        int status = 500;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(status,"INTERNAL_SERVER_ERROR",msg));
    }

}
