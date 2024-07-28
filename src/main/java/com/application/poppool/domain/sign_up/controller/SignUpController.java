package com.application.poppool.domain.sign_up.controller;

import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.sign_up.dto.response.GetInterestListResponse;
import com.application.poppool.domain.sign_up.service.SignUpService;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController implements SignUpControllerDoc {

    private final SignUpService signUpService;
    private final JwtService jwtService;

    /**
     * 회원가입
     *
     * @param signUpRequest
     */
    @Override
    @PostMapping("")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest, HttpServletResponse response) {
        log.info("회원가입");
        signUpService.signUp(signUpRequest);
        
        // 회원가입 완료 토큰 발급
        LoginResponse loginResponse = jwtService.createJwtToken(signUpRequest.getUserId(), false);
        // 헤더에 토큰 싣기
        jwtService.setHeaderAccessToken(response, loginResponse.getAccessToken());
        jwtService.setHeaderRefreshToken(response, loginResponse.getRefreshToken());
    }

    /**
     * 닉네임 중복확인
     */
    @Override
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickName) {
        log.info("닉네임 중복 확인");
        return ResponseEntity.ok(signUpService.isNicknameDuplicate(nickName));
    }

    /**
     * 성별 후보군 목록 조회
     *
     * @return
     */
    @Override
    @GetMapping("/genders")
    public ResponseEntity<List<GetGenderResponse>> getGenderList() {
        log.info("성별 후보군 목록 조회");
        return ResponseEntity.ok(signUpService.getGenderList());
    }

    /**
     * 관심사 후보군 목록 조회
     *
     * @return
     */
    @Override
    @GetMapping("/interests")
    public ResponseEntity<GetInterestListResponse> getInterestList() {
        log.info("관심사 후보군 목록 조회");
        return ResponseEntity.ok(signUpService.getInterestList());
    }

}
