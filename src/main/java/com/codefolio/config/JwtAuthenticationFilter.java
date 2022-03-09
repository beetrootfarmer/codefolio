package com.codefolio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

// 시큐리티에 UsernamePasswordAuthenticationFilter가 있음
// login요청해서 username, password 전송하면 filter가 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // private final JwtTokenProvider jwtTokenProvider;

    // @Override
    // public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    //     // 헤더에서 JWT 를 받아옵니다.
    //     String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
    //     // 유효한 토큰인지 확인합니다.
    //     if (token != null && jwtTokenProvider.validateToken(token)) {
    //         // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
    //         Authentication authentication = jwtTokenProvider.getAuthentication(token);
    //         // SecurityContext 에 Authentication 객체를 저장합니다.
    //         SecurityContextHolder.getContext().setAuthentication(authentication);
    //     }
    //     chain.doFilter(request, response);
    // }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
            System.out.println("JwtAuthentication : 로그인 시도함");
            
            
            //1. username , password 받음
            
            //2. 정상인지 로그인 시도 ( authenticationManager로로그인 시도를 하면
           
            // PrincipalDetails 호출되고 loadUserByUsername() 실행
            
            // 3. PrincipalDetails를 세션에 담고(권한 관리)
            
            // 4.JWT토큰 만들어서 응답
        return super.attemptAuthentication(request, response);
    }
}
