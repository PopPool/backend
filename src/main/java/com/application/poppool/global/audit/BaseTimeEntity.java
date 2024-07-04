package com.application.poppool.global.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "CREATE_DTM")
    private LocalDateTime createDateTime;   // 생성일시

    @LastModifiedDate
    @Column(nullable = false, name = "UPDATE_DTM")
    private LocalDateTime updateDateTime;   // 수정일시

}
