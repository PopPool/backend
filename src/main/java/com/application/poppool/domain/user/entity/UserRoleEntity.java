package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.user.enums.Role;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class UserRoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ROLE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RoleEntity role;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role userRole;

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
