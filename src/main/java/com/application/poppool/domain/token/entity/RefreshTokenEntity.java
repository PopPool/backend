package com.application.poppool.domain.token.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class RefreshTokenEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId; // 사용자 ID를 PK로 설정 (RTR 기법 적용)

    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token; // 리프레시 토큰 값, 고유 제약 조건 설정

    @Column(name = "EXPIRY_DATE_TIME", nullable = false)
    private LocalDateTime expiryDateTime; // 만료 시간

    public void updateToken(String newToken) {
        this.token = newToken;
    }


}
