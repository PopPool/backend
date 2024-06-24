package com.application.poppool.domain.user.service;

import com.application.poppool.domain.Interest.entity.InterestEntity;
import com.application.poppool.domain.Interest.enums.InterestType;
import com.application.poppool.domain.Interest.repository.InterestRepository;
import com.application.poppool.domain.Interest.service.InterestService;
import com.application.poppool.domain.auth.dto.request.SignUpRequest;
import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserInterestEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        if (userRepository.findByUserId(signUpRequest.getUserId()).isPresent()) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_MEMBER_ID);
        }

        UserEntity userEntity = UserEntity.builder()
                .userId(signUpRequest.getUserId())
                .nickName(signUpRequest.getNickName())
                .gender(signUpRequest.getGender())
                .age(signUpRequest.getAge())
                .socialType(signUpRequest.getSocialType())
                .build();


        // 사용자 관심사 추가
        this.addUserInterest(signUpRequest.getInterests(), userEntity);

        // 사용자 엔티티 저장(회원가입)
        userRepository.save(userEntity);
    }


    private void addUserInterest(Set<InterestType> userInterests, UserEntity userEntity) {

        for (InterestType interestType : userInterests) {
            InterestEntity interestEntity = interestRepository.findByInterestId(interestType)
                    .orElseThrow(() -> new BadRequestException(ErrorCode.DATA_VALIDATION_ERROR));

            UserInterestEntity userInterestEntity = UserInterestEntity.builder()
                    .userEntity(userEntity)
                    .interestEntity(interestEntity)
                    .build();

            userEntity.addInterest(userInterestEntity);
            interestEntity.addUser(userInterestEntity);
        }


    }
}
