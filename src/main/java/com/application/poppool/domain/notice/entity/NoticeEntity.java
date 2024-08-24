package com.application.poppool.domain.notice.entity;


import com.application.poppool.domain.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class NoticeEntity extends BaseEntity {

    @Id
    @Column(name = "NOTICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    public void updateNotice(UpdateNoticeRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
