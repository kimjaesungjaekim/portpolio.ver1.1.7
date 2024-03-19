package com.inno.portpolio.security.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inno.portpolio.user.mapper.UsersMapper;
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
@Service
public class UsersDetailsService implements UserDetailsService {
	
	private UsersMapper userMapper;
	
	public UsersDetailsService(UsersMapper userMapper) {
		this.userMapper=userMapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		Map<String,Object> users = userMapper.loadUserByUsername(userId);
		
		System.out.println("UsersDetailsService 거쳐 가져온 정보 체크 : " + users.get("AUTHOR_NM"));
		
		if(users.get("USER_ID") == null)
				throw new UsernameNotFoundException("가입된 회원이 아닙니다.");
			
		
		Collection<? extends GrantedAuthority> grantedAuthorities =
				Collections.singletonList(new SimpleGrantedAuthority(users.get("AUTHOR_NM")+""));
		
		return new User(users.get("USER_ID")+""
						,users.get("USER_PW")+"",grantedAuthorities);
	}

}
