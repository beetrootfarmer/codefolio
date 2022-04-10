package com.codefolio.config.exception.controller;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String s){
        super(s);
    }
}
