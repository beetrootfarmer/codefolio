package com.codefolio.config.exception.controller;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String s){
        super(s);
    }
}
