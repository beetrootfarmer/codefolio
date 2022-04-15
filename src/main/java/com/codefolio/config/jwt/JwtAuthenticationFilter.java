package com.codefolio.config.jwt;

import com.codefolio.config.exception.jwt.ExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

// 시큐리티에 UsernamePasswordAuthenticationFilter가 있음
// login요청해서 username, password 전송하면 filter가 동작함
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //헤더에서 JWT를 받아옵니다.
        String token = jwtTokenProvider.resolveToken(request);
//        try{
            //유효한 토큰인지 확인합니다.
            if (token != null && jwtTokenProvider.validateToken(token,request)) {
                //토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                //securityContext에 Authentication객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

//        jwtTokenProvider.verifyLogin(request,response);
        filterChain.doFilter(request,response);
    }
}
