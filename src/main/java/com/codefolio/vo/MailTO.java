package com.codefolio.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Getter
@NoArgsConstructor
public class MailTO {

    private String userName;
    private String address;
    private String title;
    private String message;
    private String rString;

    public MailTO(String userName,String address)throws MailException {
        this.userName=userName;
        this.address=address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void  setrString(String rString){
        this.rString=rString;
    }
}
