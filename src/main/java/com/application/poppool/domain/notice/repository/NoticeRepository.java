package com.application.poppool.domain.notice.repository;

import com.application.poppool.domain.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findAll();

}
