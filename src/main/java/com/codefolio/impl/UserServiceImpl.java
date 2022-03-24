package com.codefolio.impl;


import com.codefolio.mapper.UserMapper;
import com.codefolio.service.MailService;
import com.codefolio.vo.UserVO;
import com.codefolio.service.UserService;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
    public int joinUser(UserVO user) {return userMapper.joinUser(user);}

    @Override
    public UserVO getUser(String userEmail){
        return userMapper.getUser(userEmail);
    }

    @Override
    public List<UserVO> getAllUserData() {
        return userMapper.getAllUserData();
    }

    @Override
    public void updateUser(UserVO user){ userMapper.updateUser(user);}

    @Override
    public void deleteUser(String userId){
        userMapper.deleteUser(userId);
    }

    @Override
    public int checkEmail(String userEmail){
        return userMapper.checkEmail(userEmail);
    }

    @Override
    public int checkId(String userId){return userMapper.checkId(userId);}

    @Override
    public UserVO getUserById(String id){return userMapper.getUserById(id);}

    @Override
    public String secLogin(UserVO user){return userMapper.secLogin(user);}

    @Override
    public String checkLogin(UserVO user){return userMapper.checkLogin(user);}

    @Override
    public void updateRefToken(UserVO user){userMapper.updateRefToken(user);}


    @Override
    public void updateUserImg(UserVO user){userMapper.updateUserImg(user);};

    @Override
    public UserVO getUserByEmail(String email){return userMapper.getUserByEmail(email);}

    @Override
    public String getSaltKey(String email){return userMapper.getSaltKey(email);}

    @Override
    public String getSecString(){
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
