package com.codefolio.service;


import com.codefolio.vo.MailTO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;


public interface MailService {
    public void send(String title, String to, String templateName, HashMap<String, String> values) throws MessagingException, IOException;
    public void changePwd(MailTO mail, String acToken);
}
