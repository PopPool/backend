package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.user.dto.request.UpdateMyInterestRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyProfileRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyTailoredInfoRequest;
import com.application.poppool.domain.user.dto.response.GetProfileResponse;
import com.application.poppool.domain.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController implements UserProfileControllerDoc {

    private final UserProfileService userProfileService;

    @Override
    @GetMapping("/{userId}/profiles")
    public ResponseEntity<GetProfileResponse> getMyProfile(@PathVariable String userId) {
        return ResponseEntity.ok(userProfileService.getMyProfile(userId));
    }

    @Override
    @PutMapping("/{userId}/profiles")
    public void updateMyProfile(@PathVariable String userId,
                                @RequestBody UpdateMyProfileRequest request) {
        userProfileService.updateMyProfile(userId, request);
    }

    @Override
    @PutMapping("/{userId}/interests")
    public void updateMyInterests(@PathVariable String userId,
                                  @RequestBody @Valid UpdateMyInterestRequest updateMyInterestRequest) {
        userProfileService.updateMyInterests(userId, updateMyInterestRequest);
    }

    @Override
    @PutMapping("/{userId}/tailored-info")
    public void updateMyTailoredInfo(@PathVariable String userId,
                                     @RequestBody @Valid UpdateMyTailoredInfoRequest updateMyTailoredInfoRequest) {
        userProfileService.updateMyTailoredInfo(userId, updateMyTailoredInfoRequest);
    }

}
