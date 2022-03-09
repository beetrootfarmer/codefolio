package com.codefolio.config;

import com.codefolio.config.oauth.PrincipalOauth2UserService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;



/*
import com.pawmap.configuration.auth.PrincipalDetailsService;

/*


/*
 * 소셜 로그인 시 대략적인 프로세스
 * 1. 코드받기 (인증) 
 * 2. 엑세스 토큰 획득 (권한)
 * 3. 사용자 프로필 정보 획등
 * 4-1. 프로필 정보를 토대로 회원가입 자동 진행
 * 4-2. (이메일,전화번호,이름,아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip 등급, 일반등급) 등 요구되는 정보가 많으면 추가 정보를 form으로 받아 수동으로 회원가입 시켜야함
 * 
 */
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화하는 어노테이션

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

//	 private JwtTokenProvider jwtTokenProvider;
	 
	//oauth2 사용을 위한 의존성 주입
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;

	
//	@Autowired
//	private PrincipalDetailsService principalDetailsService;

    private final CorsFilter corsFilter;
	
	
	//시큐리티가 대신 로그인해줄때 password를 가로채기하는데,
	//해당 password가 뭘로 해쉬가 되어 회원가입하는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가능함
// 	@Override
// 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// 		auth.userDetailsService(principalDetailsService).passwordEncoder(encodedPwd);
// 	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 http.csrf().disable(); // csrf 보안 토큰 disable처리.
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
		 .and()
		 .addFilter(corsFilter)
		 .formLogin().disable()
         .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
         .addFilter(new JwtAuthenticationFilter())
         .authorizeRequests() // 요청에 대한 사용권한 체크
								.antMatchers("/user/**").authenticated()
								.antMatchers("/proj/**").authenticated()
								.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 이걸 쓰려면 user 테이블에 role 컬럼 만들고 admin과 user 구분
								//위 주소 외에는 모든 권한 허용하는 코드
								.anyRequest().permitAll();
								//권한 없는 사람이 마이페이지 접근시 로그인페이지로 
//								.and()
//								.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//				                        UsernamePasswordAuthenticationFilter.class);
				                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
//								.formLogin()
//								.loginPage("/login")
//								.usernameParameter("id") // id로 입력된 파라미터를 username에 매핑
//								.passwordParameter("pwd") 
//								.loginProcessingUrl("/login")//"/login" 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
//								.defaultSuccessUrl("/") //로그인 하면 로그인 전에 요청한 페이지로 넘어감
//								// oauth2 라이브러리를 통한 소셜 로그인을 위한 코드
//								.and()
//								.oauth2Login()
//								.loginPage("/login")
//								// 구글 로그인 완료된 후 후처리 필요. Tip. 구글로그인 완료시 하기의 정보를 받음
//								// 액세스 토큰 + 사용자 프로필정보
//								.userInfoEndpoint()
//								.userService(principalOauth2UserService);
						
	}

}