package com.codefolio.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.codefolio.vo.UserVO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails, OAuth2User{

    private UserVO user; // 콤포지션



    //일반 로그인
    public PrincipalDetails(UserVO user) {
	this.user = user;
    }   

    //소셜 로그인
    public PrincipalDetails(UserVO user,  Map<String ,Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    private Map<String ,Object> attributes;

    //해당 유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    // 계정이 잠겼을 때 사용하는 메소드
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 비밀번호 유효기간이 지났을 때 사용하는 메소드
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화여부
    @Override
    public boolean isEnabled() {
        // 현재시간-로그인시간 = 1년 초과 시 계정 비활성화 
        // 구현 X
        return true;
    }
    // 리소스 서버로 부터 받는 회원정보
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

   // User의 PrimaryKey
	@Override
	public String getName() {
		return user.getId()+"";
	}

    
}
