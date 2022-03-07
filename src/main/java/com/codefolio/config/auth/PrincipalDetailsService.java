package com.codefolio.config.auth;

import com.codefolio.impl.UserServiceImpl;
import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서 loginProcessingUrl("/login") 
// 로그인 요청이 오면 자동으로 UserDetailsService 타입의 loadUserByUsername() 메소드 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;
    // 시큐리티 session(내부 Authentication 객체(내부 UserDeails)) 구조로 들어가게된다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 시 입력받는 id를 username으로 매핑
        // SecurityCondig에서 username 파라미터를 (id)로 설정해줌
        UserVO user = userService.getUserById(username);
        if(user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
    
}
