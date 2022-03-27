package com.codefolio.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogContcoller {

    @GetMapping("")
    public void logging(){
        log.info("msg");
        log.debug("debug");
        log.error("error");
    }
}
