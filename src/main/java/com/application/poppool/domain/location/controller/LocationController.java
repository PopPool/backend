package com.application.poppool.domain.location.controller;

import com.application.poppool.domain.location.dto.response.GetViewBoundPopUpStoreListResponse;
import com.application.poppool.domain.location.dto.response.SearchPopUpStoreByMapResponse;
import com.application.poppool.domain.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController implements LocationControllerDoc {

    private final LocationService locationService;


    @Override
    @GetMapping("/search")
    public ResponseEntity<SearchPopUpStoreByMapResponse> searchPopUpStoreByMap(@AuthenticationPrincipal UserDetails userDetails,
                                                                               @RequestParam(required = false) List<Integer> categories,
                                                                               @RequestParam String query) {
        log.info("지도에서 팝업스토어 검색");
        if (query.length() < 2) { // 검색어가 두 글자 이상인 경우에만 검색 진행
            return null;
        }
        if (userDetails == null) {
            return ResponseEntity.ok(locationService.searchPopUpStoreByMap("GUEST", categories, query));
        }
        return ResponseEntity.ok(locationService.searchPopUpStoreByMap(userDetails.getUsername(), categories, query));
    }

    @Override
    @GetMapping("/popup-stores")
    public ResponseEntity<GetViewBoundPopUpStoreListResponse> getViewBoundPopUpStoreList(@RequestParam List<Integer> categories,
                                                                                         @RequestParam double northEastLat,
                                                                                         @RequestParam double northEastLon,
                                                                                         @RequestParam double southWestLat,
                                                                                         @RequestParam double southWestLon) {
        log.info("뷰 바운즈 내에 있는 팝업 스토어 정보 조회");
        return ResponseEntity.ok(locationService.getViewBoundPopUpStoreList(categories, northEastLat, northEastLon, southWestLat, southWestLon));

    }


}
