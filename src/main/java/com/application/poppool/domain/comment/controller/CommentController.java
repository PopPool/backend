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
    public void createComment(@RequestBody @Valid CreateCommentRequest request) {
        commentService.createComment(request);
    }

    @Override
    @PutMapping("")
    public void updateComment(@RequestBody @Valid UpdateCommentRequest request) {
        commentService.updateComment(request);
    }

    @Override
    @DeleteMapping("")
    public void deleteComment(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(name = "popUpStoreId") Long popUpStoreId,
                              @RequestParam(name = "commentId") Long commentId) {
        commentService.deleteComment(userDetails.getUsername(), popUpStoreId, commentId);

    }


}
