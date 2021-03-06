package com.studyveloper.baedalon;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	public PasswordEncoder passwordEncoder() {
		// bcrypt
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
