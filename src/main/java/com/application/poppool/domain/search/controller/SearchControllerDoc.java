package com.application.poppool.domain.search.controller;

import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "통합 검색 API")
public interface SearchControllerDoc {

    @Operation(summary = "통합 검색", description = "통합 검색을 합니다.")
    ResponseEntity<SearchPopUpStoreResponse> searchPopUpStore(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestParam String query);
}
