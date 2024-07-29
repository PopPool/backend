package com.application.poppool.domain.token.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "black_list_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"TOKEN"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class BlackListTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "EXPIRY_DATE_TIME")
    private LocalDateTime expiryDateTime;

}
