package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserInterestCategoryRepository extends JpaRepository<UserInterestCategoryEntity, Long> {

    @Modifying(flushAutomatically = true)
    @Query(value = "delete from UserInterestCategoryEntity u where u.id in :userInterestIdList")
    void deleteAllByUserInterestId(@Param("userInterestIdList") List<Long> userInterestIdList);

    List<UserInterestCategoryEntity> findAllByUser(UserEntity user);

}
