package com.studyveloper.baedalon.infra.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // bcrypt
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 인증 무시 경로 지정
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // css, js, img 등 정적 리소스
        web.ignoring().mvcMatchers("/favicon.ico", "/error"); // 파비콘, 오류 페이지
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 개발용
        http.authorizeRequests().anyRequest().permitAll();
    }
}
