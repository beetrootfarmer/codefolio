package com.codefolio.config;

import com.codefolio.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration  //IoC 빈(bean)등록
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Bean 메서드는 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();//rest api이므로 csrf 보안이 필요 없으므로  disable
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session을 사용하지 않음
                .and()
                .addFilter(corsFilter)  //@CrossOrigin(인증 X), 시큐리티 필터에 등록(인증 O)
                .formLogin().disable()  //formlogin이나 기본 httplogin방식을 아예 쓰지 않는다.
                .httpBasic().disable()  //Basic : cookie에 저장해서 접근 / Bearer : token으로 접근(유효시간)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))   //parameter : AuthenticationManager
                .authorizeRequests()
                .antMatchers("/user/detail/**").access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();
    }

}
