package com.application.poppool.domain.image.repository;

import com.application.poppool.domain.image.entity.CommentImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentImageRepository extends JpaRepository<CommentImageEntity, Long> {
}
