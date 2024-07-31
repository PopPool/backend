package com.application.poppool.domain.image.entity;

import com.application.poppool.domain.popup.entity.PopUpStoreEntity;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POPUP_STORE_ID", nullable = false)
    private PopUpStoreEntity popupStore;

    @Column(name = "IMAGE_URL")
    private String url;

    @Column(name = "MAIN_YN")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isMain;

}
