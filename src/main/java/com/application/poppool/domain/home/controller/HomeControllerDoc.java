package com.application.poppool.domain.home.controller;

import com.application.poppool.domain.home.dto.response.GetAllCustomPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetAllNewPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetAllPopularPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
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

@Tag(name = "홈 화면 API")
public interface HomeControllerDoc {

    @Operation(summary = "홈 화면 조회", description = "홈 화면을 조회합니다.")
    ResponseEntity<GetHomeInfoResponse> getHomeInfo(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PageableDefault(page = 0, size = 6, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);


    @Operation(summary = "맞춤 팝업 스토어 전체 보기", description = "전체 맞춤 팝업 스토어를 조회합니다.")
    ResponseEntity<GetAllCustomPopUpStoresResponse> getCustomPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "인기 팝업 스토어 전체 보기", description = "전체 인기 팝업 스토어를 조회합니다.")
    ResponseEntity<GetAllPopularPopUpStoresResponse> getPopularPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                              @PageableDefault(page = 0, size = 10, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "신규 팝업 스토어 전체 보기", description = "전체 신규 팝업 스토어를 조회합니다.")
    ResponseEntity<GetAllNewPopUpStoresResponse> getNewPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);
}
