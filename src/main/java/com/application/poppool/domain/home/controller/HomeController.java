package com.application.poppool.domain.home.controller;

import com.application.poppool.domain.home.dto.response.GetAllCustomPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetAllNewPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetAllPopularPopUpStoresResponse;
import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import com.application.poppool.domain.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController implements HomeControllerDoc {

    private final HomeService homeService;

    @Override
    @GetMapping
    public ResponseEntity<GetHomeInfoResponse> getHomeInfo(@RequestParam String userId,
                                                           @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("홈 화면 조회");
        return ResponseEntity.ok(homeService.getHomeInfo(userId, pageable));
    }

    @Override
    @GetMapping("/custom/popup-stores")
    public ResponseEntity<GetAllCustomPopUpStoresResponse> getCustomPopUpStoreList(@RequestParam String userId,
                                                                                   @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("맞춤 팝업 전체 보기");
        return ResponseEntity.ok(homeService.getCustomPopUpStoreList(userId, pageable));
    }

    @Override
    @GetMapping("/popular/popup-stores")
    public ResponseEntity<GetAllPopularPopUpStoresResponse> getPopularPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                                     @PageableDefault(page = 0, size = 10, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("인기 팝업 전체 보기");
        return ResponseEntity.ok(homeService.getPopularPopUpStoreList(userDetails.getUsername(), pageable));
    }

    @Override
    @GetMapping("/new/popup-stores")
    public ResponseEntity<GetAllNewPopUpStoresResponse> getNewPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                             @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("신규 팝업 전체 보기");
        return ResponseEntity.ok(homeService.getNewPopUpStoreList(userDetails.getUsername(), pageable));
    }

}
