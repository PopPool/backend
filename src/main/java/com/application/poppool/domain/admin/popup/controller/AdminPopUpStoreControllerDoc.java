package com.application.poppool.domain.admin.popup.controller;

import com.application.poppool.domain.admin.popup.dto.request.CreatePopUpStoreRequest;
import com.application.poppool.domain.admin.popup.dto.request.UpdatePopUpStoreRequest;
import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreDetailResponse;
import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "관리자 페이지 팝업 스토어 API")
public interface AdminPopUpStoreControllerDoc {

    @Operation(summary = "팝업 스토어 리스트 조회", description = "팝업 스토어 리스트를 조회합니다.")
    ResponseEntity<GetAdminPopUpStoreListResponse> getAdminPopUpStoreList(@RequestParam(required = false) String query,
                                                                          @PageableDefault(page = 0, size = 10, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable);

    @Operation(summary = "팝업 스토어 조회", description = "팝업 스토어를 조회합니다.")
    ResponseEntity<GetAdminPopUpStoreDetailResponse> getAdminPopUpStoreDetail(@RequestParam Long popUpStoreId);

    @Operation(summary = "팝업 스토어 등록", description = "팝업 스토어를 등록합니다.")
    void createPopUpStore(@RequestBody @Valid CreatePopUpStoreRequest request);

    @Operation(summary = "팝업 스토어 수정", description = "팝업 스토어를 수정합니다.")
    void updatePopUpStore(@RequestBody @Valid UpdatePopUpStoreRequest request);

    @Operation(summary = "팝업 스토어 삭제", description = "팝업 스토어를 삭제합니다.")
    void deletePopUpStore(@RequestParam Long popUpStoreId);
}
