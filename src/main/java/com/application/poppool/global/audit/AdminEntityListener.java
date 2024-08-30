package com.application.poppool.global.audit;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class AdminEntityListener {


    @PrePersist
    public void prePersist(BaseAdminEntity entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 생성자 설정
        entity.setCreator(authentication.getName());

        // 수정자 설정
        entity.setUpdater(authentication.getName());

        // 생성 시에 생성일시 설정
        entity.setCreateDateTime(LocalDateTime.now());

        // 수정일시도 생성 시에 설정
        entity.setUpdateDateTime(LocalDateTime.now());
    }

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

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

}
