package com.codefolio.impl;


import com.codefolio.service.MailService;
import com.codefolio.vo.MailTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void checkEmail(MailTO mail) {
        SimpleMailMessage message = new SimpleMailMessage();


        String randomString = getString();
        String title = "codefolio 에서 발송한 이메일 입니다.";
        String body = mail.getUserName()+"님의 인증 메일입니다."+"인증번호 : "+randomString;

        mail.setMessage(body);
        mail.setTitle(title);
        mail.setrString(randomString);

        message.setTo(mail.getAddress());
//        message.setFrom(""); from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
        message.setSubject(mail.getTitle());
        message.setText(mail.getMessage());

        mailSender.send(message);
    }

//    public void findPwd(MailTO mail){}


    public String getString(){
        char[] charSet = new char[]
                { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                        'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                        's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#',
                        '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0; int len = charSet.length;
        for (int i=0; i<10; i++) {
            idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
