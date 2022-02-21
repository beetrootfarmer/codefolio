package com.codefolio.impl;


import com.codefolio.mapper.UserMapper;
import com.codefolio.service.MailService;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MailService mailService;

    @Autowired
    BCryptPasswordEncoder encoder;



    //JoinUser을 하는 동안에 auto_increment에 의해 생긴 주키 값을 알아내야 함
    @Override
    public int joinUser(UserVO user) {
        return userMapper.joinUser(user);
//        log.info("newId : "+user.getUserSeq());
//        return CUtil.getAsLong(user.getUserSeq());
    }

    @Override
    public UserVO getUser(String userName){return userMapper.getUser(userName);}

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }

    @Override
    public void updateUser(UserVO user){ userMapper.updateUser(user);}

    @Override
    public void delete(String userName){
        userMapper.delete(userName);
    }

    @Override
    public int checkEmail(String userEmail){
        return userMapper.checkEmail(userEmail);
    }
    @Override
    public int checkName(String userName){return userMapper.checkName(userName);}

    @Override
    public String checkLogin(UserVO user){return userMapper.checkLogin(user);}
//
//    @Override
//    public Map<String, Object> findLoginPwd(UserVO user){
//        String userName = user.getName();
//        String userEmail = user.getEmail();
//        UserVO newuser  = userMapper.searchEmail(userEmail);
//
//        if (user == null) {
//            return Maps.of("resultCode", "F-1", "msg", "일치하는 회원이 없습니다.");
//        }
//
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
//
//        String tempLoginPasswd = sb.toString();
//
//        newuser.setPwd(tempLoginPasswd);
//
//        String mailTitle = userName + "님, 당신의 계정의 임시 패스워드 입니다.";
//        String mailBody = "임시 패스워드 : " + tempLoginPasswd;
//        mailService.send(userEmail, mailTitle, mailBody);
//
//
//        // 비밀번호 암호화해주는 메서드
//        tempLoginPasswd = encoder.encode(tempLoginPasswd);
//        //tempLoginPasswd = PawMap1124Application.encodePwd(tempLoginPasswd);
//        // 데이터 베이스 값은 암호한 값으로 저장시킨다.
//        UserVO vo = new UserVO();
//        vo.setEmail(userEmail);
//        vo.setPwd(tempLoginPasswd);
//        userMapper.updateUser(vo);
//
//
//        return Maps.of("resultCode", "S-1", "msg", "입력하신 메일로 임시 패스워드가 발송되었습니다.");
//
//    }

}
