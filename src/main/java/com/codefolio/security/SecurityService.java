package com.codefolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class SecurityService {
    private static final String SECRET_KEY="asdfasdfasdfasdfasdfasdfasdfasdfasdasdfasdfasdfasdffasdfa";

    //로그인 서비스 던질 때 같이~~
    public String createToken(String subject, long expTime){
        if(expTime<=0)throw new RuntimeException("만료 시간이 0보다 커야함");
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        // key 생성
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        //Token 생성
        return Jwts.builder()
                .setSubject(subject)        //userId등에 사용
                .signWith(signingKey,signatureAlgorithm)    //Algorithm 적용한 비밀번호
                .setExpiration(new Date(System.currentTimeMillis()+expTime))    //현재 시간 + 만료 시간
                .compact();
    }

    //토큰 검증하는(@valid) 메서드를 boolean형태로
    public String getSubject(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
