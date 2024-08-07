package com.application.poppool.domain.image.entity;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "popup_store_image")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PopUpStoreImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POPUP_STORE_ID", nullable = false)
    private PopUpStoreEntity popUpStore;

    @Column(name = "IMAGE_URL")
    private String url;

    public void setPopupStore(PopUpStoreEntity popUpStore) {
        this.popUpStore = popUpStore;
    }

}
