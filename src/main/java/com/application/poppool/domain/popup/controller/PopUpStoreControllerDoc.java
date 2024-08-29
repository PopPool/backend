package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.popup.dto.resonse.GetClosedPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetOpenPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDirectionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "팝업 스토어 API")
public interface PopUpStoreControllerDoc {

    @Operation(summary = "팝업 스토어 상세 조회", description = "팝업 스토어 상세를 조회합니다.")
    ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@RequestParam(name = "userId") String userId,
                                                                    @RequestParam(name = "commentType") CommentType commentType,
                                                                    @RequestParam(name = "popUpStoreId") Long popUpStoreId);

    @Operation(summary = "검색창 하단의 팝업 스토어 진행 중(오픈) 팝업 리스트 조회", description = "검색창 하단의 팝업 스토어 진행 중(오픈) 팝업 리스트를 조회합니다.")
    ResponseEntity<GetOpenPopUpStoreListResponse> getOpenPopUpStoreList(@RequestParam List<Category> categories,
                                                                        @PageableDefault(page = 0, size = 10, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "검색창 하단의 팝업 스토어 종료 팝업 리스트 조회", description = "검색창 하단의 팝업 스토어 종료 팝업 리스트 조회")
    ResponseEntity<GetClosedPopUpStoreListResponse> getClosedPopUpStoreList(@RequestParam List<Category> categories,
                                                                            @PageableDefault(page = 0, size = 10, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "팝업스토어 찾아가는 길", description = "팝업스토어 찾아가는 길을 조회합니다.")
    ResponseEntity<GetPopUpStoreDirectionResponse> getPopUpStoreDirection(@PathVariable Long popUpStoreId);
}
