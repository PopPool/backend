package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.category.entity.CategoryEntity;
import com.application.poppool.domain.category.enums.Category;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_interest_category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "CATEGORY_ID"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class UserInterestCategoryEntity extends BaseEntity {

    @Id
    @Column(name = "USER_INTEREST_CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CategoryEntity category;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryConverter.class)
    private Category interestCategory;

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
