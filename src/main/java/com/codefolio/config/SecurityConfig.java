package com.codefolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration  //IoC 빈(bean)등록
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Bean 메서드는 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();//rest api이므로 csrf 보안이 필요 없으므로  disable
        //스프링 시큐리티 해당 주소를 낚아채버린다. => 권한없이 접근시 login 페이지로 이동함
        http.authorizeRequests()
                    .antMatchers("/user/detail/**").authenticated()    //이 주소에서는 인증이 필요하다.
//                    .antMatchers("/Admin/**").access("hasRole('ROLE_ADMIN')")   //이 주소에 접근할 수 있는 사람에게 권한 부여
                    .anyRequest().permitAll()  //나머지는 허용
                .and()
                    .formLogin()
                    .loginPage("/user/login")  //login 화면에서 post로 정보를 가져옴
                    .usernameParameter("id")  //UserDetailService에서 받아오는 parameter로 username으로 되어있기 때문에 필요시에 바꿔준다.
                    .passwordParameter("pwd")
                    .loginProcessingUrl("/user/login")  //(login 성공)login주소가 호출이 되면, 시큐리티가 낚아채서 대신 로그인을 진행해준다. Controller에 login메서드를 만들지 않아도 됨
                .defaultSuccessUrl("/");   //login완료시 보내는 주소
    }

}
