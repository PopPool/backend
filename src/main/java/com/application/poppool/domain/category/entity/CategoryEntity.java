package com.application.poppool.domain.category.entity;


import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CATEGORY_NAME"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CategoryEntity extends BaseEntity {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

}
