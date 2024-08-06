package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "user_popupstore_view"
        , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "POPUP_STORE_ID"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class UserPopUpStoreViewEntity {

    @Id
    @Column(name = "USER_POPUP_STORE_VIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VIEWED_AT")
    private LocalDateTime viewedAt;

    @Column(name = "VIEW_CNT")
    @Builder.Default
    private long viewCount = 0;

    @Column(name = "COMMENT_CNT")
    @Builder.Default
    private long commentCount = 0;

    @Column(name = "BOOKMARK_CNT")
    @Builder.Default
    private long bookmarkCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPUP_STORE_ID")
    private PopUpStoreEntity popUpStore;

    @OneToMany(mappedBy = "userPopUpStoreView")
    private List<CommentEntity> comments;


}
