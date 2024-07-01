package com.application.poppool.global.jwt;


import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.token.service.BlackListTokenService;
import com.application.poppool.domain.token.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public static final String[] TEMPORARY_TOKEN_ALLOWED_URLS = {
            "/signup/**"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtService.getAccessToken(request);

        /** AT가 null 이 아닌 경우 */
        if (accessToken != null && jwtService.validateToken(accessToken, request)) { // 1. 토큰이 헤더에 실려왔는지, 토큰이 유효한 토큰인지 확인
            if (jwtService.isTokenBlacklisted(accessToken)){
                return; // CustomAuthenticationEntryPoint가 예외 처리하도록 함
            }
            if (jwtService.getIsTemporary(accessToken)) {
                if (!isTemporaryTokenAllowedUrl(request.getRequestURI())) { // 나머지 URL은 임시 토큰으로 접근 불가, 임시 토큰인 경우 회원가입 요청만 허용
                    return; // CustomAuthenticationEntryPoint가 예외 처리하도록 함
                }
            }
            // accessToken이 유효하면 Context에 Authentication 저장 (임시/정식 모두)
            this.setAuthentication(accessToken);

        } else {
            /** AT가 헤더에 없거나, 만료되었거나, 유효하지 않은 경우
             * RT로 AT 재발급 및 API 요청 처리
             */
            String refreshToken = jwtService.getRefreshToken(request);

            if (refreshToken != null && jwtService.validateToken(refreshToken, request)) {
                String userId = jwtService.getUserId(refreshToken);
                boolean isTemporary = jwtService.getIsTemporary(refreshToken);

                if (!jwtService.isUserRefreshTokenValid(userId, refreshToken)) { // DB에 저장된 RT와 비교해서 RT가 유효하지 않으면 예외처리
                    return; // CustomAuthenticationEntryPoint가 예외 처리하도록 함
                }
                LoginResponse loginResponse = jwtService.createJwtToken(userId, isTemporary); // AT,RT 재생성

                jwtService.setHeaderAccessToken(response, loginResponse.getAccessToken()); // AT 발급
                jwtService.setHeaderRefreshToken(response, loginResponse.getRefreshToken()); // RT 발급

                jwtService.replaceRefreshToken(userId, loginResponse.getRefreshToken()); // RT 테이블에 새로운 RT로 기존 RT 대체
                if (isTemporary) { // 임시 토큰인 경우
                    if (!isTemporaryTokenAllowedUrl(request.getRequestURI())) { // 나머지 URL은 임시 토큰으로 접근 불가, 임시 토큰인 경우 회원가입 요청만 허용
                        return; // CustomAuthenticationEntryPoint가 처리하도록 함
                    }
                }
                // accessToken이 유효하면 Context에 Authentication 저장 (임시/정식 모두)
                this.setAuthentication(loginResponse.getAccessToken());
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean isTemporaryTokenAllowedUrl(String requestUri) {
        return Arrays.stream(TEMPORARY_TOKEN_ALLOWED_URLS)
                .anyMatch(requestUri::equals);
    }
}
