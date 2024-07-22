package com.application.poppool.domain.user.controller;

import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.*;
import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    /**
     * 마이페이지 조회
     *
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<GetMyPageResponse> getMyPage(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getMyPage(userId));
    }

    /**
     * 내가 쓴 일반 코멘트 조회
     *
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/{userId}/comments")
    public ResponseEntity<GetMyCommentResponse> getMyCommentList(@PathVariable String userId,
                                                                 @PageableDefault(page = 0, size = 10, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getMyCommentList(userId, pageable));
    }

    @Override
    @GetMapping("/{userId}/popupstores/with-comments")
    public ResponseEntity<GetMyCommentedPopUpStoreListResponse> getMyCommentedPopUpStoreList(@PathVariable String userId,
                                                                                             @PageableDefault(page = 0, size = 10, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getMyCommentedPopUpStoreList(userId, pageable));
    }

    @Override
    @GetMapping("/{userId}/recent-popupstores")
    public ResponseEntity<GetMyRecentViewPopUpStoreListResponse> getMyRecentViewPopupStoreList(@PathVariable String userId,
                                                                                               @PageableDefault(page = 0, size = 10, sort = "viewedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getMyRecentViewPopUpStoreList(userId, pageable));
    }


    /**
     * 차단한 사용자 목록 조회
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    @GetMapping("/blocked")
    public ResponseEntity<GetBlockedUserListResponse> getBlockedUserList(@RequestParam String userId,
                                                                         @PageableDefault(page = 0, size = 10, sort = "blockedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getBlockedUserList(userId, pageable));
    }

    @Override
    @PostMapping("/block")
    public void blockUser(@RequestParam String blockerUserId, @RequestParam String blockedUserId) {
        userService.blockUser(blockerUserId, blockedUserId);
    }


    @Override
    @DeleteMapping("/unblock")
    public void unblockUser(@RequestParam String userId, @RequestParam String blockedUserId) {
        userService.unblockUser(userId, blockedUserId);
    }


    /**
     * 회원 탈퇴
     *
     * @param userId
     * @param request - 체크된 설문 항목
     */
    @Override
    @PostMapping("/{userId}/delete")
    public void deleteUser(@PathVariable String userId,
                           @RequestBody @Valid CheckedSurveyListRequest request) {
        userService.deleteUser(userId, request);
    }

    /**
     * 설문 항목 리스트
     *
     * @return
     */
    @Override
    @GetMapping("/withdrawl/surveys")
    public ResponseEntity<GetWithDrawlSurveyResponse> getWithDrawlSurvey() {
        return ResponseEntity.ok(userService.getWithDrawlSurvey());
    }

    /**
     * 회원 로그아웃
     *
     * @param request
     */
    @Override
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String accessToken = jwtService.getAccessToken(request);
        LocalDateTime expiryDateTime = jwtService.getExpiration(accessToken);
        userService.logout(accessToken, expiryDateTime);
    }

}
