package com.application.poppool.domain.interest.entity;


import com.application.poppool.domain.interest.enums.InterestType;
import com.application.poppool.domain.user.entity.UserInterestEntity;
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
public class InterestEntity {

    @Id
    @Column(name = "INTEREST_ID", unique = true)
    @Enumerated(EnumType.STRING)
    private InterestType interestId;

    @Column(name = "INTEREST_NAME")
    private String interestName;

    @OneToMany(mappedBy = "interestEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserInterestEntity> userInterestEntities = new HashSet<>();

    public void addUser(UserInterestEntity userInterestEntity) {
        userInterestEntities.add(userInterestEntity);
    }


}
