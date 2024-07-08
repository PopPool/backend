package com.application.poppool.domain.interest.service;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.enums.InterestCategory;
import com.application.poppool.domain.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    /**
     * 관리자가 관심사를 DB 테이블에 등록
     *
     * @param interests
     */
    @Transactional
    public void addInterest(Set<InterestCategory> interests) {
        for (InterestCategory interestCategory : interests) {
            InterestEntity interestEntity = interestRepository.findByInterestCategory(interestCategory)
                    .orElseGet(() -> interestRepository.save(
                            InterestEntity.builder()
                                    .interestCategory(interestCategory)
                                    .build()
                    ));
        }
    }
}
