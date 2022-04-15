package com.codefolio.service;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;


public interface MailService {
    public void send(String title, String to, String templateName, HashMap<String, String> values) throws MessagingException, IOException;
}
