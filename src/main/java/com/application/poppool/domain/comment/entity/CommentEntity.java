package com.application.poppool.domain.comment.entity;

import com.application.poppool.domain.comment.enums.CommentType;
import com.application.poppool.domain.image.entity.CommentImageEntity;
import com.application.poppool.domain.like.entity.LikeEntity;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.CommentTypeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CommentEntity extends BaseEntity {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LIKE_COUNT")
    @Builder.Default
    private long likeCount = 0;

    @Column(name = "TYPE")
    @Convert(converter = CommentTypeConverter.class)
    private CommentType commentType;

    @Version // 낙관적 락 (버전 정보로 동시성 이슈 해결)
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPUP_STORE_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PopUpStoreEntity popUpStore;

    @OneToMany(mappedBy = "comment") // 코멘트가 사라지면 좋아요도 없어져야함
    @Builder.Default
    private List<LikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommentImageEntity> images = new ArrayList<>();

    public void addImage(CommentImageEntity image) {
        this.images.add(image);
        image.setComment(this);
    }

    /**
     * 코멘트 수정
     */
    public void updateComment(String content) {
        this.content = content;
    }

    /**
     * 좋아요 수 증가
     */
    public void incrementLikeCount() {
        this.likeCount++;
    }

    /**
     * 좋아요 수 감소
     */
    public void decrementLikeCount() {
        this.likeCount--;
    }

}
