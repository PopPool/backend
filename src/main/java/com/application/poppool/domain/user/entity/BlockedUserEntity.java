package com.application.poppool.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"USER_ID", "BLOCKED_USER_ID"})},
        indexes = {
                @Index(name = "idx_user_id", columnList = "USER_ID"),
                @Index(name = "idx_blocked_user_id", columnList = "BLOCKED_USER_ID"),
                @Index(name = "idx_user_blocked_user", columnList = "USER_ID, BLOCKED_USER_ID")}
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class BlockedUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOCKED_USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity blockedUser;

    @Column(name = "BLOCKED_AT")
    private LocalDateTime blockedAt;

}
