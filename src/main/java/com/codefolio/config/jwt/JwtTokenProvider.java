package com.codefolio.config.jwt;

import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    // jwt를 생성하고 검증하는 컴포넌트
    // jwt에는 토큰 만료시간이나 회원 권한 정보등을 저장할 수 있다.
    private String secretKey= JwtProperties.SECRET;
    private final UserDetailsService userDetailsService;
    private final UserService userService;


    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    //login시 acToken과 refToken 갱신이 필요하다.
    public String createToken(String userEmail){
        Claims claims = Jwts.claims().setSubject(userEmail);   //Jwt payload에 저장되는 정보 단위
        Date now = new Date();
        String acToken = Jwts.builder()
                .setClaims(claims)  //정보 저장
                .setIssuedAt(now)   //토큰 발행시간 정보
                .setExpiration(new Date(now.getTime()+JwtProperties.EXPIRATION_TIME))  //setExpire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  //사용할 암호화 알고리즘과, signature에 들어갈 secret갓 세팅
                .compact();

        return acToken;
    }

    public String createRefToken(String userEmail){
        Claims claims = Jwts.claims().setSubject(userEmail);   //Jwt payload에 저장되는 정보 단위
        Date now = new Date();
        String refToken = Jwts.builder()
                .setClaims(claims)  //정보 저장
                .setIssuedAt(now)   //토큰 발행시간 정보
                .setExpiration(new Date(now.getTime()+JwtProperties.REF_EXPIRATION_TIME))  //setExpire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  //사용할 암호화 알고리즘과, signature에 들어갈 secret값 세팅
                .compact();
        UserVO userVO=new UserVO();
        userVO.setRefToken(refToken);
        userVO.setEmail(userEmail);
        userService.updateRefToken(userVO);
        return refToken;
    }

    public String createEmailToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);   //Jwt payload에 저장되는 정보 단위
        claims.put("role","ROLE_USER");  //권한설정 key/value 쌍으로 저장된다.
        Date now = new Date();
        String acToken = Jwts.builder()
                .setClaims(claims)  //정보 저장
                .setIssuedAt(now)   //토큰 발행시간 정보
                .setExpiration(new Date(now.getTime()+JwtProperties.EMAIL_EXPIRATION_TIME))  //setExpire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  //사용할 암호화 알고리즘과, signature에 들어갈 secret갓 세팅
                .compact();

        return acToken;
    }

    //jwt토큰에서 인증정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token값을 가져옵니다. "X-AUTH-TOKEN":"TOKEN값" =>refresh token은 DB에 저장
    public String resolveToken(HttpServletRequest request){
        return request.getHeader(JwtProperties.ACCESS_HEADER_STRING);
    }

    //토큰의 유효성 + 만료 일자 확인
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }


}
