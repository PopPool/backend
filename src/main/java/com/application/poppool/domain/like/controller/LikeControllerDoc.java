package com.application.poppool.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "좋아요(도움돼요) API")
public interface LikeControllerDoc {

    @Operation(summary = "코멘트 좋아요(도움돼요)", description = "코멘트 좋아요(도움돼요)")
    void likeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "commentId") Long commentId);

    @Operation(summary = "코멘트 좋아요(도움돼요) 취소", description = "코멘트 좋아요(도움돼요)를 취소합니다.")
    void unlikeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "commentId") Long commentId);

}
