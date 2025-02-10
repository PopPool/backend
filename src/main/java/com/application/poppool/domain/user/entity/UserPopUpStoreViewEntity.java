package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "user_popup_store_view"
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
    private long viewCount = 0L;

    @Column(name = "COMMENT_CNT")
    @Builder.Default
    private long commentCount = 0L;

    @Column(name = "BOOKMARK_CNT")
    @Builder.Default
    private long bookmarkCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPUP_STORE_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PopUpStoreEntity popUpStore;

    public void updateViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementCommentCount() {
        this.commentCount++;
    }

    public void decrementCommentCount() {
        this.commentCount--;
    }

    public void incrementBookmarkCount() {
        this.bookmarkCount++;
    }

    public void decrementBookmarkCount() {
        this.bookmarkCount--;
    }

}
