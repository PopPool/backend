package com.application.poppool.domain.sign_up.controller;

import com.application.poppool.domain.sign_up.dto.request.SignUpRequest;
import com.application.poppool.domain.sign_up.dto.response.GetGenderResponse;
import com.application.poppool.domain.sign_up.dto.response.GetInterestListResponse;
import com.application.poppool.domain.sign_up.service.SignUpService;
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

    /**
     * 회원가입
     * @param signUpRequest
     */
    @Override
    @PostMapping("")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("회원가입");
        signUpService.signUp(signUpRequest);
    }

    /**
     * 닉네임 중복확인
     */
    @Override
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickNameDuplicate(@RequestParam String nickName) {
        log.info("닉네임 중복 확인");
        return ResponseEntity.ok(signUpService.isNickNameDuplicate(nickName));
    }

    /**
     * 성별 후보군 목록 조회
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
     * @return
     */
    @Override
    @GetMapping("/interests")
    public ResponseEntity<GetInterestListResponse> getInterestList() {
        log.info("관심사 후보군 목록 조회");
        return ResponseEntity.ok(signUpService.getInterestList());
    }

}
