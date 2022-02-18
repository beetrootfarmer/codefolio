package com.codefolio.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public String sendSimpleMessage(String to)throws Exception;

}
