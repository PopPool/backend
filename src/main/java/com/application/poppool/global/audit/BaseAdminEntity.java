package com.application.poppool.global.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass //  상위 클래스인 BaseEntity의 필드들을 하위 엔티티 클래스에 포함
public abstract class BaseAdminEntity extends BaseTimeAdminEntity {

    @CreatedBy
    @Column(name = "CREATOR", updatable = false)
    private String creator;                // 생성자

    @LastModifiedBy
    @Column(name = "UPDATER")
    private String updater;                // 수정자

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        super.setCreateDateTime(createDateTime);
    }

    public void updateUpdateDateTime(LocalDateTime updateDateTime) {
        setUpdateDateTime(updateDateTime);
    }

}
