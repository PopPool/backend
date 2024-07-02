package com.application.poppool.domain.user.controller;


import com.application.poppool.domain.user.dto.request.UpdateMyInterestRequest;
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
public class UserProfileController implements UserProfileControllerDoc{

    private final UserProfileService userProfileService;

    @Override
    @GetMapping("/{user-id}/profiles")
    public ResponseEntity<GetProfileResponse> getMyProfile(@PathVariable("user-id") String userId) {
        return ResponseEntity.ok(userProfileService.getMyProfile(userId));
    }

    @Override
    @PutMapping("/{user-id}/profiles")
    public void updateMyProfile(@PathVariable("user-id") String userId) {
        userProfileService.getMyProfile(userId);
    }

    @Override
    @PutMapping("/{user-id}/interests")
    public void updateMyInterests(@PathVariable("user-id") String userId,
                                  @RequestBody @Valid UpdateMyInterestRequest updateMyInterestRequest) {
        userProfileService.updateMyInterests(userId,updateMyInterestRequest);
    }

}
