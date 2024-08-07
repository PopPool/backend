package com.application.poppool.domain.like.controller;

import com.application.poppool.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController implements LikeControllerDoc {

    private final LikeService likeService;

    @PostMapping("/likes")
    public void likeComment(@RequestParam(name = "userId") String userId, @RequestParam(name = "commentId") Long commentId) {
        likeService.likeComment(userId, commentId);
    }

    @DeleteMapping("/likes")
    public void unlikeComment(@RequestParam(name = "userId") String userId, @RequestParam(name = "commentId") Long commentId) {
        likeService.likeComment(userId, commentId);
    }
}
