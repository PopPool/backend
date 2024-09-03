package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.category.enums.Category;
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
    public ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@RequestParam(name = "userId") String userId,
                                                                           @RequestParam(name = "commentType") CommentType commentType,
                                                                           @PathVariable Long popUpStoreId) {
        return ResponseEntity.ok(popUpStoreService.getPopUpStoreDetail(userId, commentType, popUpStoreId));
    }

    @Override
    @GetMapping("/open")
    public ResponseEntity<GetOpenPopUpStoreListResponse> getOpenPopUpStoreList(@RequestParam List<Category> categories,
                                                                               @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(popUpStoreService.getOpenPopUpStoreList(categories, pageable));
    }

    @Override
    @GetMapping("/closed")
    public ResponseEntity<GetClosedPopUpStoreListResponse> getClosedPopUpStoreList(@RequestParam List<Category> categories,
                                                                                   @PageableDefault(page = 0, size = 20, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(popUpStoreService.getClosedPopUpStoreList(categories, pageable));
    }

    @Override
    @GetMapping("/{popUpStoreId}/directions")
    public ResponseEntity<GetPopUpStoreDirectionResponse> getPopUpStoreDirection(@PathVariable Long popUpStoreId) {
        return ResponseEntity.ok(popUpStoreService.getPopUpStoreDirection(popUpStoreId));
    }


}
