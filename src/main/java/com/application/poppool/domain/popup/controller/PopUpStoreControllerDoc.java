package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.popup.dto.resonse.GetAllPopUpListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팝업 스토어 API")
public interface PopUpStoreControllerDoc {

    @Operation(summary = "팝업 스토어 상세 조회", description = "팝업 스토어 상세를 조회합니다.")
    ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@RequestParam(name = "userId") String userId,
                                                                    @RequestParam(name = "commentType") CommentType commentType,
                                                                    @RequestParam(name = "popUpStoreId") Long popUpStoreId);

    @Operation(summary = "검색창 하단의 팝업 스토어 리스트 전체 조회", description = "검색창 하단의 팝업 스토어 리스트 전체를 조회합니다.")
    ResponseEntity<GetAllPopUpListResponse> getAllPopUpList(@PageableDefault(page = 0, size = 6, sort = "createDateTime",direction = Sort.Direction.DESC) Pageable pageable);
}
