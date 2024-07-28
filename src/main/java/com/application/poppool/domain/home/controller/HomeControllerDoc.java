package com.application.poppool.domain.home.controller;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "홈 화면 API")
public interface HomeControllerDoc {

    @Operation(summary = "홈 화면 조회", description = "홈 화면을 조회합니다.")
    ResponseEntity<GetHomeInfoResponse> getHomeInfo(@PathVariable String userId);

}
