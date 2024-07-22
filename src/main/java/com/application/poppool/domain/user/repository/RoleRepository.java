package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.RoleEntity;
import com.application.poppool.domain.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(Role role);
}
