package com.codefolio.impl;


import com.codefolio.service.MailService;
import com.codefolio.vo.MailTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Async
    @Override
    public void send(String title, String to, String templateName, HashMap<String, String> values) throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        //메일 제목 설정
        helper.setSubject(title);

        //수신자 설정
        helper.setTo(to);

        //템플릿에 전달할 데이터 설정
        Context context = new Context();
        values.forEach((key, value) -> {
            context.setVariable(key, value);
        });

        //메일 내용 설정 : 템플릿 프로세스
        String html = templateEngine.process(templateName, context);
        helper.setText(html, true);

        //메일 보내기
        mailSender.send(message);
    }

//        @Override
//    @Async
//    public void changePwd(MailTO mail, String acToken){
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        String title = "비밀번호 변경 이메일입니다.";
//        String body = mail.getUserName()+"님의 비밀번호를 변경\n "+"<button type=\"button\" class=\"navyBtn\" onClick=\"codefolio.com/"+acToken+"'\">전송</button>";
//
//        mail.setMessage(body);
//        mail.setTitle(title);
//        mail.setrString(acToken);
//
////        message.setFrom(""); from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
//        message.setSubject(mail.getTitle());
//        message.setText(htmlStr,"utf-8","html");
//
//        mailSender.send(message);
//    }

    @Async
    @Override
    public void changePwd(MailTO mail, String acToken) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
        message.setSubject("[codefolio] 비밀번호 변경 이메일 입니다. ", "utf-8");
        String htmlStr = "<h1 style=\"color: lightblue;\">hi everyone~~</h1>"
                + "<a href=\"http://www.naver.com\">naver</a>"
                +"<span>"+acToken+"</span>";
        //내용설정
        message.setText(htmlStr, "utf-8", "html");

        //TO 설정
        message.addRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(mail.getAddress(), "utf-8"));

        mailSender.send(message);

    } catch(MessagingException e) {
        e.printStackTrace();
    } catch(UnsupportedEncodingException e) {
        e.printStackTrace();
    }
}

//
//    public String getString(){
//        char[] charSet = new char[]
//                { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
//                        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
//                        'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
//                        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
//                        's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#',
//                        '$', '%', '^', '&' };
//
//        StringBuffer sb = new StringBuffer();
//        SecureRandom sr = new SecureRandom();
//        sr.setSeed(new Date().getTime());
//        int idx = 0; int len = charSet.length;
//        for (int i=0; i<10; i++) {
//            idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
//            sb.append(charSet[idx]);
//        }
//        return sb.toString();
//    }
}
