package com.application.poppool.domain.popup.entity;

import com.application.poppool.domain.bookmark.entity.BookMarkPopupStoreEntity;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pop_up_store")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PopUpStoreEntity extends BaseEntity {

    @Id
    @Column(name = "POP_UP_STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "POP_UP_STORE_NAME")
    private String name;

    @Column(name = "DESC")
    private String desc;

    @Column(name = "START_DT")
    private LocalDateTime startDate;

    @Column(name = "END_DT")
    private LocalDateTime endDate;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(mappedBy = "popUpStore")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "popUpStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkPopupStoreEntity> bookmarkPopUpStores = new ArrayList<>();

}
