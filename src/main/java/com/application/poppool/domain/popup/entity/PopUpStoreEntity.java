package com.application.poppool.domain.popup.entity;

import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.user.entity.BookMarkPopUpStoreEntity;
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
    @Column(name = "POPUP_STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POPUP_STORE_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "START_DT")
    private LocalDateTime startDate;

    @Column(name = "END_DT")
    private LocalDateTime endDate;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CLOSED_YN")
    private String closedYn;

    @Builder.Default
    @OneToMany(mappedBy = "popUpStore")
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "popUpStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkPopUpStoreEntity> bookMarkPopUpStores = new ArrayList<>();

}
