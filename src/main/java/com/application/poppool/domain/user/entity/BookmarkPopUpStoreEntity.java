package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_bookmark_popup_store", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "POPUP_STORE_ID"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class BookmarkPopUpStoreEntity extends BaseEntity {

    @Id
    @Column(name = "BOOKMARK_POPUP_STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPUP_STORE_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PopUpStoreEntity popUpStore;


}
