package com.application.poppool.domain.comment.controller;

import com.application.poppool.domain.comment.dto.request.CreateCommentRequest;
import com.application.poppool.domain.comment.dto.request.UpdateCommentRequest;
import com.application.poppool.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController implements CommentControllerDoc {

    private final CommentService commentService;

    @Override
    @PostMapping("")
    public void createComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid CreateCommentRequest request) {
        log.info("코멘트 작성");
        commentService.createComment(userDetails.getUsername(), request);
    }

    @Override
    @PutMapping("")
    public void updateComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateCommentRequest request) {
        log.info("코멘트 수정");
        commentService.updateComment(userDetails.getUsername(), request);
    }

    @Override
    @DeleteMapping("")
    public void deleteComment(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(name = "popUpStoreId") Long popUpStoreId,
                              @RequestParam(name = "commentId") Long commentId) {
        log.info("코멘트 삭제");
        commentService.deleteComment(userDetails.getUsername(), popUpStoreId, commentId);

    }


}
