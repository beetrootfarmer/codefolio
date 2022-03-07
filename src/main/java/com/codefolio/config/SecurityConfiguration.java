package com.codefolio.config;

import com.codefolio.config.auth.PrincipalDetailsService;
import com.codefolio.config.oauth.PrincipalOauth2UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/*
import com.pawmap.configuration.auth.PrincipalDetailsService;
import com.pawmap.configuration.oauth.PrincipalOauth2UserService;

/*

import com.pawmap.configuration.oauth.PrincipalOauth2UserService;

/*
 * 소셜 로그인 시 대략적인 프로세스
 * 1. 코드받기 (인증) 
 * 2. 엑세스 토큰 획득 (권한)
 * 3. 사용자 프로필 정보 획등
 * 4-1. 프로필 정보를 토대로 회원가입 자동 진행
 * 4-2. (이메일,전화번호,이름,아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip 등급, 일반등급) 등 요구되는 정보가 많으면 추가 정보를 form으로 받아 수동으로 회원가입 시켜야함
 * 
 */

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록됨 (시큐리티 기능 활성화를 위한 어노테이션)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화하는 어노테이션
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	//oauth2 사용을 위한 의존성 주입
// 	@Autowired
// 	private PrincipalOauth2UserService principalOauth2UserService;
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;

	
	@Autowired
	private PrincipalDetailsService principalDetailsService;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	@Bean
	public BCryptPasswordEncoder encodePwd(){
    	return new BCryptPasswordEncoder();
    }

	
	
	
	//시큐리티가 대신 로그인해줄때 password를 가로채기하는데,
	//해당 password가 뭘로 해쉬가 되어 회원가입하는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가능함
// 	@Override
// 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// 		auth.userDetailsService(principalDetailsService).passwordEncoder(encodedPwd);
// 	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		//.antMatchers("/css/**", "/js/**", "/images/**", "/webfonts/**").permitAll()
		
		http.csrf().disable();
		http.authorizeRequests()
								.antMatchers("/user/detail/**").authenticated()
								.antMatchers("/proj/**").authenticated()
								.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 이걸 쓰려면 user 테이블에 role 컬럼 만들고 admin과 user 구분
								//위 주소 외에는 모든 권한 허용하는 코드
								.anyRequest().permitAll()
								//권한 없는 사람이 마이페이지 접근시 로그인페이지로 
								.and()
								.formLogin()
								.loginPage("/login")
								.usernameParameter("id") // id로 입력된 파라미터를 username에 매핑
								.passwordParameter("pwd") 
								.loginProcessingUrl("/login")//"/login" 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
								.defaultSuccessUrl("/") //로그인 하면 로그인 전에 요청한 페이지로 넘어감
								// oauth2 라이브러리를 통한 소셜 로그인을 위한 코드
								.and()
								.oauth2Login()
								.loginPage("/login")
								// 구글 로그인 완료된 후 후처리 필요. Tip. 구글로그인 완료시 하기의 정보를 받음
								// 액세스 토큰 + 사용자 프로필정보
								.userInfoEndpoint()
								.userService(principalOauth2UserService);
						
	}

//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/webfonts/**");
//	}
}