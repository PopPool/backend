package com.application.poppool.domain.popup.entity;

import com.application.poppool.domain.comment.entity.CommentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pop_up_store")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PopUpStoreEntity {

    @Id
    @Column(name = "POP_UP_STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "POP_UP_STORE_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "popUpStore")
    private List<CommentEntity> comments;


}
