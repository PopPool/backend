package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.interest.entity.InterestEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_interest", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "INTEREST_ID"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class UserInterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "INTEREST_ID", nullable = false)
    private InterestEntity interestEntity;
}
