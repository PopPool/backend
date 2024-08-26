package com.application.poppool.global.audit;

import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserRoleEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
public class AdminEntityListener {

    private final UserRepository userRepository;

    @PreUpdate
    public void preUpdate(BaseAdminEntity entity) {
        // admin 계정에서만 업데이트
        if (isAdmin()) {
            entity.updateUpdateDateTime(LocalDateTime.now());
        }
        // 조건이 맞지 않으면 아무것도 하지 않음 (기존 값 유지)
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userRepository.findByUserId(authentication.getName())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 해당 유저의 권한 가져오기
        Set<UserRoleEntity> userRoleList = user.getUserRoles();

        // 관리자 여부 체크
        return userRoleList.stream().anyMatch(role -> role.getUserRole().name().equals("ADMIN"));
    }

}
