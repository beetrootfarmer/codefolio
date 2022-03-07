// package com.codefolio.config;

// import lombok.extern.slf4j.*;
// import org.springframework.security.core.context.*;

// import java.io.IOException;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;

// import com.codefolio.config.security.JwtTokenProvider;

// import org.springframework.security.core.*;
// import org.springframework.web.filter.*;

// @Slf4j
// public class JwTAuthenticationFilter extends GenericFilterBean {
//     private JwtTokenProvider jwtTokenProvider;

//     // Jwt Provier 주입
//     public void JwtAuthenticationFilter( JwtTokenProvider jwtTokenProvider ) {
//         this.jwtTokenProvider = jwtTokenProvider;
//     }

//     /**
//      * <pre>
//      * Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
//      * </pre>
//      *
//      * @param request
//      * @param response
//      * @param filterChain
//      * @throws IOException
//      * @throws ServletException
//      */
//     @Override
//     public void doFilter( ServletRequest request, ServletResponse response, FilterChain filterChain ) throws IOException, ServletException {
//         String token = jwtTokenProvider.resolveToken( (HttpServletRequest) request );

//         if( token != null && jwtTokenProvider.validateToken( token ) ) {
//             Authentication auth = jwtTokenProvider.getAuthentication( token );
//             SecurityContextHolder.getContext().setAuthentication( auth );
//         }

//         filterChain.doFilter( request, response );
//     }

// }

