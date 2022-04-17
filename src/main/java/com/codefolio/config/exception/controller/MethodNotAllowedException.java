package com.codefolio.config.exception.controller;

public class MethodNotAllowedException extends RuntimeException{
    public MethodNotAllowedException(String s){
        super(s);
    }
}
