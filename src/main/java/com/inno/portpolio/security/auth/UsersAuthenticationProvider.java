package com.inno.portpolio.security.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inno.portpolio.security.model.UsersDetailsService;

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
@RequiredArgsConstructor
public class UsersAuthenticationProvider implements AuthenticationProvider {
	
	private final UsersDetailsService usersDetailService;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String id = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password);
		
		
		UserDetails users = usersDetailService.loadUserByUsername(id);
		
		if(users !=null) 
			if(!passwordEncoder.matches(password,users.getPassword())
							) throw new UsernameNotFoundException("비밀번호가 일치 하지 않습니다.");
			
		return new UsernamePasswordAuthenticationToken(users, password, users.getAuthorities());
	}
	
	
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
