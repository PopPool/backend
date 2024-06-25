package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.auth.dto.request.SignUpRequest;
import com.application.poppool.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDoc{

    private final UserService userService;

    /**
     * 회원가입
     * @param signUpRequest
     */
    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("회원가입");
        userService.signUp(signUpRequest);
    }

    /**
     * 닉네임 중복확인
     */
    @GetMapping
    public ResponseEntity<Boolean> checkNickNameDuplicate(@RequestParam String nickName) {
        log.info("닉네임 중복 확인");
        return ResponseEntity.ok(userService.isNickNameDuplicate(nickName));
    }

}
