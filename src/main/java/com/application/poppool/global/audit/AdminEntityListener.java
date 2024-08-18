package com.application.poppool.global.audit;

import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class AdminEntityListener {

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
        if (authentication.getName().equals("ADMIN")) {
            return true;
        }
        return false;
    }

}
