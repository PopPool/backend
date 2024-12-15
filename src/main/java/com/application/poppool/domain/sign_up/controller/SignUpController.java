package com.application.poppool.domain.sign_up.controller;

import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetCategoryListResponse;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.sign_up.service.SignUpService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController implements SignUpControllerDoc {

    private final SignUpService signUpService;
    /**
     * 회원가입
     *
     * @param signUpRequest
     */
    @Override
    @PostMapping("")
    public void signUp(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid SignUpRequest signUpRequest, HttpServletResponse response) throws IOException {
        log.info("회원가입");
        signUpService.signUp(userDetails.getUsername(), signUpRequest, response);
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
    @GetMapping("/categories")
    public ResponseEntity<GetCategoryListResponse> getCategoryList() {
        log.info("카테고리 목록 조회");
        return ResponseEntity.ok(signUpService.getCategoryList());
    }

}
