package com.application.poppool.domain.category.entity;


import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CATEGORY"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class CategoryEntity extends BaseEntity {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryConverter.class)
    private Category category;

}
