package com.codefolio.config.oauth;

import java.util.Map;
import java.util.Optional;

import com.codefolio.config.auth.PrincipalDetails;
import com.codefolio.config.oauth.provider.GoogleUserInfo;
import com.codefolio.config.oauth.provider.KakaoUserInfo;
import com.codefolio.config.oauth.provider.NaverUserInfo;
import com.codefolio.config.oauth.provider.OAuth2UserInfo;
import com.codefolio.service.UserService;
import com.codefolio.vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	@Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    UserService userService;
        
@Override
public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
	OAuth2User oauth2User = super.loadUser(userRequest);
	

	//소셜로그인 요청에 따른 정보 설정
	String name = "";
	String providerId = "";
	OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
			name = (String)oauth2User.getAttributes().get("name");
			providerId = oAuth2UserInfo.getProviderId();
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
			name = oAuth2UserInfo.getName();
			providerId = oAuth2UserInfo.getProviderId();
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			System.out.println("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
			name = oAuth2UserInfo.getName();
			providerId = oAuth2UserInfo.getProviderId();
		
		}else {
			System.out.println("구글, 페이스북, 네이버, 카카오 외의 로그인 요청입니다!");
		}
		

	//회원가입 강제 진행@!
	String provider = userRequest.getClientRegistration().getClientId(); // google
	String pwd = bcryptPasswordEncoder.encode("블라블라");
	String email = oauth2User.getAttribute("email");
		String a[] = email.split("@");
		String id = provider + "_" + a[0];
	String role = "ROLE_USER";

	// 일치하는 이름이 없다면 회원가입 진행
	UserVO user = userService.getUserByEmail(name);
	
	// UserVO 에 위 정보를 넣을 Builder를 만들어준다
	if(user == null) {
		user = UserVO.builder()
				.id(id)
				.name(name)
				.pwd(pwd)
				.email(email)
				.role(role)
				.provider(provider)
				.providerId(providerId)
				.build();
		userService.joinUser(user);
	}

		// 이 리턴값이 security session의 Authentication 에 담긴다
		return new PrincipalDetails(user, oauth2User.getAttributes());
	//userRequest에 담긴 것 :  
	// ClientRegistration
	// AccessToken
	// Attributes : 사용자 정보 (sub, name, email)

	// 구글 로그인 완료 -> OAuth2의 Client라이브러리를 통해 AccessToken 요청
	// -> loadUser() 메소드로 user Attributes(회원프로필) 받아옴
	}
}