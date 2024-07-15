package com.application.poppool.domain.user.controller;

import com.application.poppool.domain.user.dto.request.CheckedSurveyListRequest;
import com.application.poppool.domain.user.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "마이페이지의 회원 API")
public interface UserControllerDoc {

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    ResponseEntity<GetMyPageResponse> getMyPage(@PathVariable String userId);

    @Operation(summary = "내가 쓴 일반 코멘트 조회", description = "내가 쓴 일반 코멘트를 조회합니다.")
    ResponseEntity<GetMyCommentResponse> getMyCommentList(@PathVariable String userId,
                                                          Pageable pageable);

    @Operation(summary = "내가 코멘트 단 팝업 스토어 리스트 조회", description = "내가 코멘트 단 팝업 스토어 리스트를 조회합니다.")
    ResponseEntity<GetMyCommentedPopUpStoreListResponse> getMyCommentedPopUpStoreList(@PathVariable String userId,
                                                                                      @PageableDefault(page = 0, size = 10, sort = "createDateTime",direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "최근 본 팝업 스토어 리스트 조회", description = "최근 본 팝업 스토어 리스트를 조회합니다.")
    ResponseEntity<GetMyRecentViewPopUpStoreListResponse> getMyRecentViewPopupStoreList(@PathVariable String userId,
                                                                                        @PageableDefault(page = 0, size = 10, sort = "viewedAt",direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    void deleteUser(@PathVariable String userId, @RequestBody @Valid CheckedSurveyListRequest request);

    @Operation(summary = "회원 탈퇴 설문 항목 조회", description = "회원 탈퇴 설문 항목을 조회합니다.")
    ResponseEntity<GetWithDrawlSurveyResponse> getWithDrawlSurvey();

    @Operation(summary = "회원 로그아웃", description = "회원 로그아웃을 진행합니다.")
    void logout(HttpServletRequest request);

}
