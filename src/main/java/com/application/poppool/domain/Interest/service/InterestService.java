package com.application.poppool.domain.Interest.service;

import com.application.poppool.domain.Interest.entity.InterestEntity;
import com.application.poppool.domain.Interest.enums.InterestType;
import com.application.poppool.domain.Interest.repository.InterestRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    @Transactional
    public void addInterest(Set<InterestType> interests) {
        for (InterestType interestType : interests) {
            InterestEntity interestEntity = interestRepository.findByInterestId(interestType)
                    .orElseGet(() -> interestRepository.save(
                            InterestEntity.builder()
                                    .interestId(interestType)
                                    .interestName(interestType.getName())
                                    .build()
                    ));
        }
    }
}
