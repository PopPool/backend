package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.user.enums.WithDrawlSurvey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_withdrawl")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class WithDrawalSurveyEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SURVEY")
    @Enumerated(EnumType.STRING)
    private WithDrawlSurvey survey;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "COUNT")
    private Long count;

    public void incrementCount() {
        this.count++;
    }
}
