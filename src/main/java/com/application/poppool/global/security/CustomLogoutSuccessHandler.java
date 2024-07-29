package com.application.poppool.global.security;

import com.application.poppool.global.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 로그아웃 성공 후 추가적인 처리를 방지하기 위해 응답 종료
        String message = "로그아웃에 성공했습니다.";


        // 공통 예외 응답
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
        response.getWriter().flush();
        response.getWriter().close();
        
    }
}
