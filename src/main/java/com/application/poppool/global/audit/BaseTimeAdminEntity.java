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
@MappedSuperclass //  상위 클래스인 BaseTimeEntity의 필드들을 하위 엔티티 클래스에 포함
@EntityListeners(AdminEntityListener.class)
public abstract class BaseTimeAdminEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "CREATE_DTM")
    private LocalDateTime createDateTime;   // 생성일시

    @LastModifiedDate
    @Column(nullable = false, name = "UPDATE_DTM")
    private LocalDateTime updateDateTime;   // 수정일시

    protected void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }
    protected void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

}
