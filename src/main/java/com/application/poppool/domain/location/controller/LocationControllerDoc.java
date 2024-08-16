package com.application.poppool.domain.location.controller;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.location.dto.response.GetViewBoundPopUpStoreListResponse;
import com.application.poppool.domain.location.dto.response.SearchPopUpStoreByMapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "지도 API")
public interface LocationControllerDoc {

    @Operation(summary = "지도에서 검색", description = "지도에서 검색합니다.")
    ResponseEntity<SearchPopUpStoreByMapResponse> searchPopUpStoreByMap(@RequestParam Category category,
                                                                        @RequestParam String query);

    @Operation(summary = "뷰 바운즈 내에 있는 팝업 스토어 정보 조회", description = "뷰 바운즈 내에 있는 팝업 스토어 정보를 조회합니다.")
    ResponseEntity<GetViewBoundPopUpStoreListResponse> getViewBoundPopUpStoreList(@RequestParam Category category,
                                                                                  @RequestParam double northEastLat,
                                                                                  @RequestParam double northEastLon,
                                                                                  @RequestParam double southWestLat,
                                                                                  @RequestParam double southWestLon);

}
