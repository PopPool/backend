package com.application.poppool.domain.comment.entity;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CommentEntity {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IMAGE")
    private String image;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "POP_UP_STORE_ID")
    private PopUpStoreEntity popUpStore;
}
