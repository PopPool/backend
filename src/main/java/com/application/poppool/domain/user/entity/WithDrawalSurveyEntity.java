package com.application.poppool.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "QUESTION")
    private String question;

}
