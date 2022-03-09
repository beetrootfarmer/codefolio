package com.codefolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//시큐리티 기능 구현 전 임시로 기능 막아놓음
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CodefolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodefolioApplication.class, args);
    }


}
