package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.user.dto.request.UpdateMyProfileRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyTailoredInfoRequest;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.global.audit.BaseEntity;
import com.application.poppool.global.converter.GenderConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @Column(name = "USER_ID", unique = true)
    @NotBlank // 애플리케이션 레벨에서 유효성 검사
    private String userId; // 회원 ID

    @Column(name = "PASSWORD")
    @Builder.Default
    private String password = ""; // Default 값으로 빈 문자열 설정

    @Column(name = "NICKNAME")
    private String nickname; // 닉네임

    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl; // 프로필 이미지

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    @Column(name = "AGE")
    private int age; // 연령

    @Column(name = "TERM_AGREE_YN")
    private String isTermAgree; // 약관동의 여부

    @Column(name = "INSTAGRAM_ID")
    private String instagramId; // 인스타 ID

    @Column(name = "INTRO")
    private String intro; // 자기소개

    @Column(name = "SOCIAL_TYPE")
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleEntity> userRoles = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterestCategoryEntity> userInterestCategories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BookMarkPopUpStoreEntity> bookMarkPopupStores = new ArrayList<>();

    public void updateMyProfile(UpdateMyProfileRequest updateMyProfileRequest) {
        this.profileImageUrl = updateMyProfileRequest.getProfileImageUrl();
        this.nickname = updateMyProfileRequest.getNickname();
        this.instagramId = updateMyProfileRequest.getInstagramId();
        this.intro = updateMyProfileRequest.getIntro();
    }

    public void updateMyTailoredInfo(UpdateMyTailoredInfoRequest updateMyTailoredInfoRequest) {
        this.gender = updateMyTailoredInfoRequest.getGender();
        this.age = updateMyTailoredInfoRequest.getAge();
    }

    // 유저의 관심사 추가
    public void addInterestCategory(UserInterestCategoryEntity userInterestCategory) {
        // db에 아직 저장되지 않은 상태에서는 user.getInterestCategory()하면 컬렉션을 1차캐시에서 순수한 객체 그대로 가져오기 때문에 빈 컬렉션이 반환된다.
        // 따라서 양방향 매핑에서는 양 쪽 다 값을 셋팅해주어야함
        userInterestCategories.add(userInterestCategory);
        userInterestCategory.setUser(this);
    }

    // 권한 부여
    public void addUserRole(UserRoleEntity userRole) {
        userRoles.add(userRole);
        userRole.setUser(this);
    }

}
