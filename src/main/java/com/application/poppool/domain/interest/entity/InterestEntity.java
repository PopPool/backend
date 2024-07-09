package com.application.poppool.domain.interest.entity;


import com.application.poppool.domain.interest.enums.InterestCategory;
import com.application.poppool.domain.user.entity.UserInterestEntity;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.InterestCategoryConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "interest")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class InterestEntity extends BaseEntity {

    @Id
    @Column(name = "INTEREST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    @Column(name = "INTEREST_CATEGORY", unique = true)
    @Convert(converter = InterestCategoryConverter.class)
    private InterestCategory interestCategory;

    @OneToMany(mappedBy = "interest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserInterestEntity> userInterestEntities = new HashSet<>();

    public void addUser(UserInterestEntity userInterestEntity) {
        userInterestEntities.add(userInterestEntity);
    }


}
