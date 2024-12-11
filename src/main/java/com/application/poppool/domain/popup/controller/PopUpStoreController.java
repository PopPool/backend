package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.popup.dto.resonse.GetClosedPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetOpenPopUpStoreListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDirectionResponse;
import com.application.poppool.domain.popup.service.PopUpStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/popup")
public class PopUpStoreController implements PopUpStoreControllerDoc {

    private final PopUpStoreService popUpStoreService;

    @Override
    @GetMapping("/{popUpStoreId}/detail")
    public ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@AuthenticationPrincipal UserDetails userDetails,
                                                                           @RequestParam(name = "commentType") CommentType commentType,
                                                                           @PathVariable Long popUpStoreId) {
        if (userDetails == null) {
            return ResponseEntity.ok(popUpStoreService.getPopUpStoreDetail("GUEST", commentType, popUpStoreId));
        }
        return ResponseEntity.ok(popUpStoreService.getPopUpStoreDetail(userDetails.getUsername(), commentType, popUpStoreId));
    }

    @Override
    @GetMapping("/open")
    public ResponseEntity<GetOpenPopUpStoreListResponse> getOpenPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                               @RequestParam(required = false) List<Integer> categories,
                                                                               @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("오픈한 팝업 스토어 리스트 조회");
        if (userDetails == null) {
            return ResponseEntity.ok(popUpStoreService.getOpenPopUpStoreList("GUEST", categories, pageable));
        }
        return ResponseEntity.ok(popUpStoreService.getOpenPopUpStoreList(userDetails.getUsername(), categories, pageable));
    }

    @Override
    @GetMapping("/closed")
    public ResponseEntity<GetClosedPopUpStoreListResponse> getClosedPopUpStoreList(@AuthenticationPrincipal UserDetails userDetails,
                                                                                   @RequestParam(required = false) List<Integer> categories,
                                                                                   @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("종료된 팝업 스토어 리스트 조회");
        if (userDetails == null) {
            return ResponseEntity.ok(popUpStoreService.getClosedPopUpStoreList("GUEST", categories, pageable));
        }
        return ResponseEntity.ok(popUpStoreService.getClosedPopUpStoreList(userDetails.getUsername(), categories, pageable));
    }

    @Override
    @GetMapping("/{popUpStoreId}/directions")
    public ResponseEntity<GetPopUpStoreDirectionResponse> getPopUpStoreDirection(@PathVariable Long popUpStoreId) {
        return ResponseEntity.ok(popUpStoreService.getPopUpStoreDirection(popUpStoreId));
    }


}
