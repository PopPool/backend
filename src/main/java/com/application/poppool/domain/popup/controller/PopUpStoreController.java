package com.application.poppool.domain.popup.controller;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.popup.dto.resonse.GetAllPopUpListResponse;
import com.application.poppool.domain.popup.dto.resonse.GetPopUpStoreDetailResponse;
import com.application.poppool.domain.popup.service.PopUpStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/popup")
public class PopUpStoreController implements PopUpStoreControllerDoc {

    private final PopUpStoreService popUpStoreService;

    @Override
    @GetMapping("/detail")
    public ResponseEntity<GetPopUpStoreDetailResponse> getPopUpStoreDetail(@RequestParam(name = "userId") String userId,
                                                                          @RequestParam(name = "commentType") CommentType commentType,
                                                                          @RequestParam(name = "popupStoreId") Long popUpStoreId) {
        return ResponseEntity.ok(popUpStoreService.getPopUpStoreDetail(userId, commentType, popUpStoreId));
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<GetAllPopUpListResponse> getAllPopUpList(@PageableDefault(page = 0, size = 6, sort = "startDate",direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(popUpStoreService.getAllPopUpList(pageable));

    }

}
