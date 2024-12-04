package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.user.dto.request.UpdateMyInterestCategoryRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyProfileRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyTailoredInfoRequest;
import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import com.application.poppool.domain.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController implements UserProfileControllerDoc {

    private final UserProfileService userProfileService;

    @Override
    @GetMapping("/profiles")
    public ResponseEntity<GetProfileResponse> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userProfileService.getMyProfile(userDetails.getUsername()));
    }

    @Override
    @PutMapping("/profiles")
    public void updateMyProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestBody UpdateMyProfileRequest request) {
        userProfileService.updateMyProfile(userDetails.getUsername(), request);
    }

    @Override
    @PutMapping("/interests")
    public void updateMyInterestCategory(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody @Valid UpdateMyInterestCategoryRequest request) {
        userProfileService.updateMyInterestCategory(userDetails.getUsername(), request);
    }

    @Override
    @PutMapping("/tailored-info")
    public void updateMyTailoredInfo(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestBody @Valid UpdateMyTailoredInfoRequest request) {
        userProfileService.updateMyTailoredInfo(userDetails.getUsername(), request);
    }

}
