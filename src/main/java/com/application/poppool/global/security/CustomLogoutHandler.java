package com.application.poppool.global.security;

import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.LogoutFailureException;
import com.application.poppool.global.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;



@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        try {
            String token = jwtService.getToken(request);
            if (token != null && jwtService.validateToken(token, request)) { // 토큰 검증
                //LocalDateTime expiryDateTime = jwtService.getExpiration(token);
                userService.logout(token);
            }
            /** 토큰 예외로 인한 로그아웃 실패 */
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | DecodingException e) {
            throw new LogoutFailureException(ErrorCode.LOGOUT_FAILED.getMessage(), e);
            /** 서버 에러로 인한 로그아웃 실패 */
        } catch (Exception e) {
            throw new LogoutFailureException(ErrorCode.LOGOUT_FAILED.getMessage(), e);
        }
    }
}
