package com.application.poppool.domain.search.controller;

import com.application.poppool.domain.search.dto.SearchPopUpStoreResponse;
import com.application.poppool.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController implements SearchControllerDoc {

    private final SearchService searchService;

    @GetMapping("/popup-stores")
    public ResponseEntity<SearchPopUpStoreResponse> searchPopUpStore(@RequestParam String query) {
        log.info("팝업스토어 검색");
        return ResponseEntity.ok(searchService.searchPopUpStore(query));
    }


}
