package com.application.poppool.domain.like.controller;

import com.application.poppool.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController implements LikeControllerDoc {

    private final LikeService likeService;

    @PostMapping("/likes")
    public void likeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "commentId") Long commentId) {
        likeService.likeComment(userDetails.getUsername(), commentId);
    }

    @DeleteMapping("/likes")
    public void unlikeComment(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "commentId") Long commentId) {
        likeService.unlikeComment(userDetails.getUsername(), commentId);
    }
}
