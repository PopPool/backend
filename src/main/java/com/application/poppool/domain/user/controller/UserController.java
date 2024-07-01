package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.token.service.BlackListTokenService;
import com.application.poppool.domain.user.dto.response.GetMyPageResponse;
import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final JwtService jwtService;
    private final BlackListTokenService blackListTokenService;

    /**
     * 마이페이지 조회
     * @param userId
     * @return
     */
    @GetMapping("")
    public ResponseEntity<GetMyPageResponse> getMyPage(@RequestParam String userId) {
        return ResponseEntity.ok(userService.getMyPage(userId));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String accessToken = jwtService.getAccessToken(request);
        LocalDateTime expiryDateTime = jwtService.getExpiration(accessToken);
        userService.logout(accessToken, expiryDateTime);
    }

}
