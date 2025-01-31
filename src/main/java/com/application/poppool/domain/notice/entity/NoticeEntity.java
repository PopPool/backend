package com.application.poppool.domain.notice.entity;


import com.application.poppool.domain.admin.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.global.audit.BaseAdminEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class NoticeEntity extends BaseAdminEntity {

    @Id
    @Column(name = "NOTICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    @NotBlank
    private String title;

    @Lob
    @Column(name = "CONTENT")
    @NotBlank
    private String content;

    public void updateNotice(UpdateNoticeRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
