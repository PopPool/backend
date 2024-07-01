package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import com.application.poppool.domain.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController implements UserProfileControllerDoc{

    private final UserProfileService userProfileService;

    @Override
    @GetMapping("/{user-id}/profiles")
    public ResponseEntity<GetProfileResponse> getMyProfile(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok(userProfileService.getMyProfile(userId));
    }

}
