package com.application.poppool.domain.admin.popup.controller;

import com.application.poppool.domain.admin.popup.dto.request.CreatePopUpStoreRequest;
import com.application.poppool.domain.admin.popup.dto.request.UpdatePopUpStoreRequest;
import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreDetailResponse;
import com.application.poppool.domain.admin.popup.dto.response.GetAdminPopUpStoreListResponse;
import com.application.poppool.domain.admin.popup.service.AdminPopUpStoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/popup-stores")
public class AdminPopUpStoreController implements AdminPopUpStoreControllerDoc{

    private final AdminPopUpStoreService adminPopUpStoreService;

    @Override
    @GetMapping("")
    public ResponseEntity<GetAdminPopUpStoreListResponse> getAdminPopUpStoreList(@RequestParam(required = false) String query,
                                                                                 @PageableDefault(page = 0, size = 10, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(adminPopUpStoreService.getAdminPopUpStoreList(query, pageable));
    }

    @Override
    @GetMapping("/detail")
    public ResponseEntity<GetAdminPopUpStoreDetailResponse> getAdminPopUpStoreDetail(Long popUpStoreId) {
        return ResponseEntity.ok(adminPopUpStoreService.getAdminPopUpStoreDetail(popUpStoreId));
    }

    @Override
    @PostMapping("")
    public void createPopUpStore(@RequestBody @Valid CreatePopUpStoreRequest request) {
        log.info("Create PopUpStore");
        adminPopUpStoreService.createPopUpStore(request);
    }

    @Override
    @PutMapping("")
    public void updatePopUpStore(@RequestBody @Valid UpdatePopUpStoreRequest request) {
        log.info("Update PopUpStore");
        adminPopUpStoreService.updatePopUpStore(request);
    }

    @Override
    @DeleteMapping("")
    public void deletePopUpStore(@RequestParam Long popUpStoreId) {
        log.info("Delete PopUpStore");
        adminPopUpStoreService.deletePopUpStore(popUpStoreId);
    }

}
