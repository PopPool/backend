package com.application.poppool.domain.comment.controller;

import com.application.poppool.domain.comment.dto.request.CreateCommentRequest;
import com.application.poppool.domain.comment.dto.request.UpdateCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "코멘트 API")
public interface CommentControllerDoc {

    @Operation(summary = "일반 코멘트 작성", description = "일반 코멘트를 작성합니다.")
    void createComment(@RequestBody @Valid CreateCommentRequest request);

    @Operation(summary = "일반 코멘트 수정", description = "일반 코멘트를 수정합니다.")
    void updateComment(@RequestBody @Valid UpdateCommentRequest request);

    @Operation(summary = "일반 코멘트 삭제", description = "일반 코멘트를 삭제합니다.")
    void deleteComment(@RequestParam(name = "userId") String userId,
                       @RequestParam(name = "popUpStoreId") Long popUpStoreId,
                       @RequestParam(name = "commentId") Long commentId);

}
