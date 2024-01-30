package com.spring.board.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//login이 성공하면 홈 화면으로 redirecting

//로그인이 성공했을때 실행할 로직 정의
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession httpSession = request.getSession();
        //athentication 객체 안에는 유저객체가 들어가있고 여기서 getName은 email을 의미한다.
        httpSession.setAttribute("email",authentication.getName());
        response.sendRedirect("/");
    }
}
