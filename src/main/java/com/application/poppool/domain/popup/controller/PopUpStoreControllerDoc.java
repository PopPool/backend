package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.popup.dto.resonse.*;
import com.application.poppool.global.enums.PopUpSortCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "팝업 스토어 API")
public interface PopUpStoreControllerDoc {

    @Operation(summary = "팝업 스토어 상세 조회", description = "팝업 스토어 상세를 조회합니다.")
    ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@AuthenticationPrincipal UserDetails userDetails,
                                                                    @PathVariable Long popUpStoreId,
                                                                    @RequestParam(name = "commentType") CommentType commentType,
                                                                    @RequestParam(required = false, defaultValue = "true") boolean viewCountYn);

    @Operation(summary = "팝업 스토어 코멘트 전체 보기", description = "팝업 스토어 코멘트 전체를 조회합니다.")
    ResponseEntity<GetAllPopUpStoreCommentsResponse> getAllPopUpStoreCommentsResponse(@AuthenticationPrincipal UserDetails userDetails,
                                                                                      @RequestParam(name = "commentType") CommentType commentType,
                                                                                      @PathVariable Long popUpStoreId,
                                                                                      @PageableDefault(page = 0, size = 10, sort = "createDateTime", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "검색창 하단의 팝업 스토어 진행 중(오픈) 팝업 리스트 조회", description = "검색창 하단의 팝업 스토어 진행 중(오픈) 팝업 리스트를 조회합니다.")
    ResponseEntity<GetOpenPopUpStoreListResponse> getOpenPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                        @RequestParam(required = false)List<Integer> categories,
                                                                        @RequestParam(name = "sortCode", required = false) List<PopUpSortCode> sortCodes,
                                                                        @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "검색창 하단의 팝업 스토어 종료 팝업 리스트 조회", description = "검색창 하단의 팝업 스토어 종료 팝업 리스트 조회")
    ResponseEntity<GetClosedPopUpStoreListResponse> getClosedPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @RequestParam(required = false) List<Integer> categories,
                                                                            @RequestParam(name = "sortCode", required = false) List<PopUpSortCode> sortCodes,
                                                                            @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "팝업스토어 찾아가는 길", description = "팝업스토어 찾아가는 길을 조회합니다.")
    ResponseEntity<GetPopUpStoreDirectionResponse> getPopUpStoreDirection(@PathVariable Long popUpStoreId);


}
