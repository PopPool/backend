package com.application.poppool.domain.popup.entity;

import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.image.entity.CommentImageEntity;
import com.application.poppool.domain.image.entity.PopUpStoreImageEntity;
import com.application.poppool.domain.user.entity.BookMarkPopUpStoreEntity;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.BooleanToYNConverter;
import com.application.poppool.global.converter.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "popup_store")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PopUpStoreEntity extends BaseEntity {

    @Id
    @Column(name = "POPUP_STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POPUP_STORE_NAME")
    private String name;

    @Lob
    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "MAIN_IMAGE_URL")
    private String mainImageUrl;

    @Column(name = "START_DT")
    private LocalDateTime startDate;

    @Column(name = "END_DT")
    private LocalDateTime endDate;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(name = "CLOSED_YN")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isClosed;

    @Column(name = "VIEW_CNT")
    @Builder.Default
    private long viewCount = 0;

    @Column(name = "BOOKMARK_CNT")
    @Builder.Default
    private long bookmarkCount = 0;

    @Column(name = "COMMNET_CNT")
    @Builder.Default
    private long commentCount = 0;

    @Version // 낙관적 락 (버전 정보로 동시성 이슈 해결)
    private Long version;

    @Builder.Default
    @OneToMany(mappedBy = "popUpStore")
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "popUpStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkPopUpStoreEntity> bookMarkPopUpStores = new ArrayList<>();

    @OneToMany(mappedBy = "popUpStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PopUpStoreImageEntity> images = new ArrayList<>();

    public void addImage(PopUpStoreImageEntity image) {
        images.add(image);
        image.setPopupStore(this);
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
