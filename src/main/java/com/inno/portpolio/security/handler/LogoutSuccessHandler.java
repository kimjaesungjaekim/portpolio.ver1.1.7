package com.inno.portpolio.security.handler;

import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("여기서 세션을 끊고 요즘엔 jwt 토큰 같은거 만료시키고 정리합니다.");
        
//        response.getWriter("<script>alert('로그아웃 하셨습니다. 로그인 페이지로 이동합니다.');</script>");
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
