package com.codefolio.config.auth;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUserName 함수가 실행
// "/login" (name) => Security가 login을 낚아챔 => PrincipalDetailsService => loadUserByUsername 메서드로 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    //시큐리티 session(내부 Authentication(내부 UserDetails))
    //login 끝나고 처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : "+username);
        UserVO user = userService.getUserById(username);
        if(user!=null){
            return new PrincipalDetails(user);
        }
        return null;
    }
}
