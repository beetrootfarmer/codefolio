package com.codefolio.config.jwt;

import com.codefolio.service.TokenService;
import com.codefolio.service.UserService;
import com.codefolio.vo.TokenVO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;


//ACTOKEN과 별개로 REFTOKEN은 DB에서 꺼내와 검증해야하기 떄문에
//시큐리티 필터를 타는 ACTOKEN과는 별개로 REFTOKEN에 맞는 유효성 검증 객체가 필요했다.
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TokenService tokenService;

    private String secretKey= JwtProperties.SECRET;


    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //login시 acToken과 refToken 갱신이 필요하다.

    //토큰에서 회원 정보 추출
    public String getUserPk(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }catch (NullPointerException e){
            log.info(e.getMessage());
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }catch(ExpiredJwtException e){
            log.error("getUserPK : "+e.getMessage());
            return "getUserPK : Expired Token";
        }
    }
    //header에서 ACTOKEN추출
    public String resolveRefToken(HttpServletRequest request){
        return request.getHeader(JwtProperties.ACCESS_HEADER_STRING);
    }

    public String createAcToken(String userUUID){
        Claims claims = Jwts.claims().setSubject(userUUID); // JWT payload 에 저장되는 정보단위
        claims.put("roles", "ROLE_USER"); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        //Access Token
        String acToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        TokenVO tokenVO = TokenVO.builder().UUID(userUUID).acToken(acToken).build();
        tokenService.updateAcToken(tokenVO);
        return acToken;
    }

    public String createRefToken(String userUUID){
        Claims claims = Jwts.claims().setSubject(userUUID); // JWT payload 에 저장되는 정보단위
        claims.put("roles", "ROLE_USER"); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        //Ref Token
        String refToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.REF_EXPIRATION_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        TokenVO tokenVO = TokenVO.builder().UUID(userUUID).refToken(refToken).build();
        tokenService.updateRefToken(tokenVO);
        return refToken;
    }


    //토큰의 유효성 + 만료 일자 확인
    public boolean validateRefToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token. at validation Token");
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }catch (Exception e) {
            return false;
        }
        return false;
    }


    public String refDate(){
        LocalDateTime now = LocalDateTime.now().plusNanos(JwtProperties.REF_EXPIRATION_TIME*1000000);
        String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formatNow;
    }

}
