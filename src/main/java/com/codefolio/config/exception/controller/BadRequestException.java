package com.codefolio.config.exception.controller;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String s){
        super(s);
    }
}
