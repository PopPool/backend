package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.enums.InterestCategory;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.InterestCategoryConverter;
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
public class UserInterestEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "INTEREST_ID", nullable = false)
    private InterestEntity interest;

    @Column(name = "INTEREST_CATEGORY")
    @Convert(converter = InterestCategoryConverter.class)
    private InterestCategory interestCategory;
}
