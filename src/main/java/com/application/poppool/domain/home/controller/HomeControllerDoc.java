package com.application.poppool.domain.home.controller;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "홈 화면 API")
public interface HomeControllerDoc {

    @Operation(summary = "홈 화면 조회", description = "홈 화면을 조회합니다.")
    ResponseEntity<GetHomeInfoResponse> getHomeInfo(@PathVariable String userId,
                                                    @PageableDefault(page = 0, size = 6, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable);


    @Operation(summary = "맞춤 팝업 스토어 전체 보기", description = "전체 맞춤 팝업 스토어를 조회합니다.")
    ResponseEntity<GetHomeInfoResponse> getCustomPopUpStoreList(@RequestParam String userId,
                                                                @PageableDefault(page = 0, size = 6, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "인기 팝업 스토어 전체 보기", description = "전체 인기 팝업 스토어를 조회합니다.")
    ResponseEntity<GetHomeInfoResponse> getPopularPopUpStoreList(@PageableDefault(page = 0, size = 6, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "신규 팝업 스토어 전체 보기", description = "전체 신규 팝업 스토어를 조회합니다.")
    ResponseEntity<GetHomeInfoResponse> getNewPopUpStoreList(@PageableDefault(page = 0, size = 6, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable);
}
