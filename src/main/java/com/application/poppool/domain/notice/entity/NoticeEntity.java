package com.application.poppool.domain.notice.entity;


import com.application.poppool.domain.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class NoticeEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    public void updateNotice(UpdateNoticeRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
