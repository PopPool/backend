package com.application.poppool.global.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass //  상위 클래스인 BaseEntity의 필드들을 하위 엔티티 클래스에 포함
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(name = "CREATOR", updatable = false)
    private String creator;                // 생성자

    @LastModifiedBy
    @Column(name = "UPDATER")
    private String updater;                // 수정자

}
