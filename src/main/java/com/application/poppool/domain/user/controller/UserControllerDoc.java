package com.application.poppool.domain.user.controller;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.*;
import com.application.poppool.global.enums.CommentSortCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Tag(name = "마이페이지의 회원 API")
public interface UserControllerDoc {

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    ResponseEntity<GetMyPageResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "내가 쓴 일반/인스타 코멘트 조회", description = "내가 쓴 일반/인스타 코멘트를 조회합니다.")
    ResponseEntity<GetMyCommentResponse> getMyCommentList(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestParam CommentType commentType,
                                                          @RequestParam(name = "sortCode", required = false) List<CommentSortCode> sortCode,
                                                          Pageable pageable);

    @Operation(summary = "코멘트 단 유저의 일반/인스타 코멘트 전체 조회", description = "코멘트 단 유저의 일반/인스타 코멘트를 전체 조회합니다.")
    ResponseEntity<GetCommenterCommentListResponse> GetCommenterCommentList(@RequestParam String commenterId,
                                                                            @RequestParam CommentType commentType,
                                                                            @PageableDefault(page = 0, size = 20, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "찜한 팝업 스토어 리스트 조회", description = "찜한 팝업 스토어 리스트를 조회합니다.")
    ResponseEntity<GetBookMarkPopUpStoreListResponse> getBookMarkedPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                                  @PageableDefault(page = 0, size = 20, sort = "updateDateTime", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "팝업 스토어 찜", description = "팝업 스토어를 찜합니다.")
    void addPopUpStoreBookmark(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long popUpStoreId);

    @Operation(summary = "팝업 스토어 찜 취소", description = "팝업 스토어 찜을 취소합니다.")
    void deletePopUpStoreBookmark(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long popUpStoreId);

    @Operation(summary = "최근 본 팝업 스토어 리스트 조회", description = "최근 본 팝업 스토어 리스트를 조회합니다.")
    ResponseEntity<GetMyRecentViewPopUpStoreListResponse> getMyRecentViewPopupStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                                        @PageableDefault(page = 0, size = 20, sort = "viewedAt", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "차단한 사용자 목록 조회", description = "차단한 사용자 목록을 조회합니다.")
    ResponseEntity<GetBlockedUserListResponse> getBlockedUserList(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @PageableDefault(page = 0, size = 20, sort = "blockedAt", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "사용자 차단", description = "사용자를 차단합니다.")
    void blockUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String blockedUserId);

    @Operation(summary = "사용자 차단 해제", description = "사용자 차단을 해제합니다.")
    void unblockUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String blockedUserId);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    void deleteUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid CheckedSurveyListRequest request) throws IOException;

    @Operation(summary = "회원 탈퇴 설문 항목 조회", description = "회원 탈퇴 설문 항목을 조회합니다.")
    ResponseEntity<GetWithDrawlSurveyResponse> getWithDrawlSurvey();

    @Operation(summary = "회원 로그아웃", description = "회원 로그아웃을 진행합니다.")
    void logout(HttpServletRequest request);

}
