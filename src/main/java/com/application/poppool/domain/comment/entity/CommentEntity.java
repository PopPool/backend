package com.application.poppool.domain.comment.entity;

import com.application.poppool.domain.like.entity.LikeEntity;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.global.audit.BaseEntity;
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

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Version // 낙관적 락 (버전 정보로 동시성 이슈 해결)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "POPUP_STORE_ID")
    private PopUpStoreEntity popUpStore;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true) // 코멘트가 사라지면 좋아요도 없어져야함
    private List<LikeEntity> likes = new ArrayList<>();


    /**
     * 좋아요 수 증가
     */
    public void addLike(){
        this.likeCount++;
    }

    /**
     * 좋아요 수 감소
     */
    public void reduceLike(){
        this.likeCount--;
    }
    
}
