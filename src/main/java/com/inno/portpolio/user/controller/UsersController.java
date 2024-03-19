package com.inno.portpolio.user.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inno.portpolio.common.enumpkg.ServiceResult;
import com.inno.portpolio.user.service.UsersService;
import com.inno.portpolio.user.vo.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 07.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2024.03.07.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Controller
@RequiredArgsConstructor
@Slf4j
public class UsersController {
	
	private final UsersService service;
	
	@GetMapping("/login") 
    public String welcomeLoginPage() {
    	
    	return "login/login";
    }

    @GetMapping("/login/signUpForm")
    public String signUpForm() {
    	
        return "/login/signup";
    }
    
    @PostMapping("/login/signup")
    @ResponseBody
    public HashMap<String, Object> signUp(
    	 @RequestBody UserVO user
    	) {
    	
    	ServiceResult res;
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	try {
    		 res = service.register(user);
    		 
    		 if(res.equals(ServiceResult.OK)) {
    			 map.put("result",res);
    			 map.put("message","회원가입 등록 성공 ");
    		 }else if(res.equals(ServiceResult.DUPLICATED)) {
    			 map.put("result",res);
    			 map.put("message","중복된 아이디입니다. ");
    		 }else {
    			 map.put("result",res);
    			 map.put("message","회원가입 등록 실패 "); 
    		 }
    		 
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return map;
    }
}
