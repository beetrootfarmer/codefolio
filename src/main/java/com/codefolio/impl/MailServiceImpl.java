package com.codefolio.impl;


import com.codefolio.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
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

}
