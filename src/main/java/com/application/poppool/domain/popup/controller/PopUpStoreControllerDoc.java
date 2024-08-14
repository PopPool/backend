package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.popup.dto.resonse.GetAllPopUpListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팝업 스토어 상세 API")
public interface PopUpStoreControllerDoc {

    ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@RequestParam(name = "userId") String userId,
                                                                    @RequestParam(name = "isInstagram") boolean isInstagram,
                                                                    @RequestParam(name = "popUpStoreId") Long popUpStoreId);
    ResponseEntity<GetAllPopUpListResponse> getAllPopUpList(@PageableDefault(page = 0, size = 6, sort = "createDateTime",direction = Sort.Direction.DESC) Pageable pageable);
}
