package com.codefolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//시큐리티 기능 구현 전 임시로 기능 막아놓음
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CodefolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodefolioApplication.class, args);
    }

    //@Bean은 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    //순환 참조 오류때문에 인코더를 밖으로 뺐음
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
