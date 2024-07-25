package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


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
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VIEWED_AT")
    private LocalDateTime viewedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPUP_STORE_ID")
    private PopUpStoreEntity popUpStore;


}
