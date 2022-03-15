package com.codefolio.config.jwt;




public interface JwtProperties{
	String SECRET = "cos"; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 60000*60; // 60분 (1/1000초) 
	int REF_EXPIRATION_TIME = 7*24*60*60*1000L;//토큰 유효시간 7일
	String TOKEN_PREFIX = "Bearer ";
	String ACCESS_HEADER_STRING = "AccessToken";
	String REFRESH_HEADER_STRING = "RefreshToken";
}
