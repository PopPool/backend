package com.application.poppool.domain.token.entity;

import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "apple_refresh_token")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class AppleRefreshTokenEntity extends BaseEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token; // 리프레시 토큰 값, 고유 제약 조건 설정

}
