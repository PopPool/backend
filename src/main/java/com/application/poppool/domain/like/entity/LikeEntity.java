package com.application.poppool.domain.like.entity;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "COMMENT_ID"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class LikeEntity extends BaseEntity {

    @Id
    @Column(name = "LIKE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) //외래 키가 생성될 테이블의 컬럼명을 지정
    private CommentEntity comment;

}
