package com.application.poppool.domain.image.entity;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment_image")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CommentImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private CommentEntity comment;

    @Column(name = "IMAGE_URL")
    private String url;

    public void setComment(CommentEntity comment) {
        this.comment =comment;
    }

}
