package com.codefolio.service;


import com.codefolio.vo.MailTO;


public interface MailService {
    public void checkEmail(MailTO mail);
    public void changePwd(MailTO mail,String acToken);
}
