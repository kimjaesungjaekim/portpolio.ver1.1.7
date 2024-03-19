package com.inno.portpolio.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.inno.portpolio.security.auth.UsersAuthenticationProvider;
import com.inno.portpolio.security.handler.AccessDeniedHandler;
import com.inno.portpolio.security.handler.FailHandler;
import com.inno.portpolio.security.handler.SuccessHandler;
import com.inno.portpolio.security.model.UsersDetailsService;
import com.inno.portpolio.user.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;
/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 07.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2023.12.02.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class PortpolioSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UsersMapper mapper;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	};
	
	@Bean
	public FailHandler failHandler() {
		return new FailHandler();
	};
	
	@Bean
	public SuccessHandler successlHandler() {
		return new SuccessHandler();
	};
	
	@Bean
	public UsersAuthenticationProvider usersAuthentication() {
		return new UsersAuthenticationProvider(new UsersDetailsService(mapper),passwordEncoder());
	};
	
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler();
    };

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new com.inno.portpolio.security.handler.LogoutSuccessHandler();
        
    };
    
    // 검증
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usersAuthentication());
    }
    
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
    		.authorizeRequests()
	    		.antMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
	    		.antMatchers("/login/**","/users/**").permitAll()
	    		.antMatchers("/main/**","/review/**","/question/**","/information/**").hasAnyRole("USER","ADMIN")
	    		.antMatchers("/admin/**").hasRole("ADMIN")
	    		.anyRequest().authenticated()
	    	.and()
	    		.exceptionHandling()
	    			.accessDeniedHandler(accessDeniedHandler())
			.and()
			.formLogin()
	        .loginPage("/login")
	        .loginProcessingUrl("/loginProc")
	        .usernameParameter("userId")
	        .passwordParameter("userPw")
	        .successHandler(successlHandler())
	        .failureHandler(failHandler())
	    .and()
	        .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessHandler(logoutSuccessHandler())
	    .and()
	    	.csrf().disable();
    }
}
