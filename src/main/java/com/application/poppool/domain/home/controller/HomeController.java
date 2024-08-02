package com.application.poppool.domain.home.controller;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController implements HomeControllerDoc{

    private final HomeService homeService;

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<GetHomeInfoResponse> getHomeInfo(@PathVariable String userId) {
        log.info("홈 화면 조회");
        return ResponseEntity.ok(homeService.getHomeInfo(userId));
    }

}
