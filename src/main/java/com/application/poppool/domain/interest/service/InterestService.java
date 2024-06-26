package com.application.poppool.domain.interest.service;

import com.application.poppool.domain.interest.entity.InterestEntity;
import com.application.poppool.domain.interest.enums.InterestType;
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
    public void addInterest(Set<InterestType> interests) {
        for (InterestType interestType : interests) {
            InterestEntity interestEntity = interestRepository.findByInterestId(interestType)
                    .orElseGet(() -> interestRepository.save(
                            InterestEntity.builder()
                                    .interestId(interestType)
                                    .interestName(interestType.getInterestName())
                                    .build()
                    ));
        }
    }
}
