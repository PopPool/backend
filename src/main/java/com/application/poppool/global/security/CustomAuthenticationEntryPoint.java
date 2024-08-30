package com.application.poppool.global.security;

import com.application.poppool.global.exception.ExceptionResponse;
import com.application.poppool.global.exception.UnAuthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String errorMessage = "인증 정보가 유효하지 않습니다."; // 기본 에러 메시지

        // UnAuthorizedException으로 던져지면
        if (authException instanceof UnAuthorizedException) {
            errorMessage = authException.getMessage();
        }

        // 공통 예외 응답
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(errorMessage)
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));

    }
}

