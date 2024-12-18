package com.application.poppool.domain.user.controller;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.*;
import com.application.poppool.domain.user.service.UserService;
import com.application.poppool.global.enums.CommentSortCode;
import com.application.poppool.global.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


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
     * @param userDetails
     * @return
     */
    @Override
    @GetMapping("/my-page")
    public ResponseEntity<GetMyPageResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {

        log.info("마이페이지 조회");
        if (userDetails == null) {
            return ResponseEntity.ok(userService.getMyPage("GUEST"));
        }
        return ResponseEntity.ok(userService.getMyPage(userDetails.getUsername()));
    }

    /**
     * 내가 쓴 일반/인스타 코멘트 조회
     *
     * @param userDetails
     * @return
     */
    @Override
    @GetMapping("/comments")
    public ResponseEntity<GetMyCommentResponse> getMyCommentList(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestParam CommentType commentType,
                                                                 @RequestParam(name = "sortCode", required = false) List<CommentSortCode> sortCodes,
                                                                 @PageableDefault(page = 0, size = 20, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getMyCommentList(userDetails.getUsername(), commentType, sortCodes, pageable));
    }

    @Override
    @GetMapping("/{commenterId}/comments")
    public ResponseEntity<GetOtherUserCommentListResponse> GetOtherUserCommentList(@RequestParam String commenterId,
                                                                                   @RequestParam CommentType commentType,
                                                                                   @PageableDefault(page = 0, size = 20, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getOtherUserCommentList(commenterId, commentType, pageable));
    }


    @Override
    @GetMapping("/bookmark-popupstores")
    public ResponseEntity<GetBookMarkPopUpStoreListResponse> getBookMarkedPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                                         @PageableDefault(page = 0, size = 20, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getBookMarkedPopUpStoreList(userDetails.getUsername(), pageable));
    }

    @Override
    @PostMapping("/bookmark-popupstores")
    public void addPopUpStoreBookmark(@AuthenticationPrincipal UserDetails userDetails, Long popUpStoreId) {
        userService.addPopUpStoreBookmark(userDetails.getUsername(), popUpStoreId);
    }

    @Override
    @DeleteMapping("/bookmark-popupstores")
    public void deletePopUpStoreBookmark(@AuthenticationPrincipal UserDetails userDetails, Long popUpStoreId) {
        userService.deletePopUpStoreBookmark(userDetails.getUsername(), popUpStoreId);
    }

    @Override
    @GetMapping("/recent-popupstores")
    public ResponseEntity<GetMyRecentViewPopUpStoreListResponse> getMyRecentViewPopupStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                                               @PageableDefault(page = 0, size = 20, sort = "viewedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getMyRecentViewPopUpStoreList(userDetails.getUsername(), pageable));
    }


    /**
     * 차단한 사용자 목록 조회
     *
     * @param
     * @param pageable
     * @return
     */
    @Override
    @GetMapping("/blocked")
    public ResponseEntity<GetBlockedUserListResponse> getBlockedUserList(@AuthenticationPrincipal UserDetails userDetails,
                                                                         @PageableDefault(page = 0, size = 20, sort = "blockedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(userService.getBlockedUserList(userDetails.getUsername(), pageable));
    }

    @Override
    @PostMapping("/block")
    public void blockUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String blockedUserId) {
        userService.blockUser(userDetails.getUsername(), blockedUserId);
    }


    @Override
    @DeleteMapping("/unblock")
    public void unblockUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String blockedUserId) {
        userService.unblockUser(userDetails.getUsername(), blockedUserId);
    }


    /**
     * 회원 탈퇴
     *
     * @param
     * @param request - 체크된 설문 항목
     */
    @Override
    @PostMapping("/delete")
    public void deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestBody @Valid CheckedSurveyListRequest request) throws IOException {
        userService.deleteUser(userDetails.getUsername(), request);
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
        log.info("logout");
        String token = jwtService.getToken(request);
        userService.logout(token);
    }

}
