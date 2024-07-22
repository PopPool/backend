package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.bookmark.entity.BookMarkPopupStoreEntity;
import com.application.poppool.domain.comment.entity.CommentEntity;
import com.application.poppool.domain.user.dto.request.UpdateMyProfileRequest;
import com.application.poppool.domain.user.dto.request.UpdateMyTailoredInfoRequest;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.domain.user.enums.Role;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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

    @Column(name = "EMAIL")
    private String email; // 소셜로그인에서 받아온 이메일

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    @Column(name = "AGE")
    private Integer age; // 연령

    @Column(name = "TERM_AGREE_YN")
    private String isTermAgree; // 약관동의 여부

    @Column(name = "INSTAGRAM_ID")
    private String instagramId;

    @Column(name = "INTRO")
    private String intro; // 자기소개

    @Column(name = "SOCIAL_TYPE")
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleEntity> userRoles;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInterestEntity> userInterestEntities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkPopupStoreEntity> bookMarkPopupStores = new ArrayList<>();

    public void updateMyProfile(UpdateMyProfileRequest updateMyProfileRequest) {
        this.profileImage = updateMyProfileRequest.getProfileImage();
        this.nickname = updateMyProfileRequest.getNickname();
        this.email = updateMyProfileRequest.getEmail();
        this.instagramId = updateMyProfileRequest.getInstagramId();
        this.intro = updateMyProfileRequest.getIntro();
    }

    public void updateMyTailoredInfo(UpdateMyTailoredInfoRequest updateMyTailoredInfoRequest) {
        this.gender = updateMyTailoredInfoRequest.getGender();
        this.age = updateMyTailoredInfoRequest.getAge();
    }

    // 유저의 관심사 추가
    public void addInterest(UserInterestEntity userInterestEntity) {
        userInterestEntities.add(userInterestEntity);
    }

    // 권한 부여
    public void addUserRole(UserRoleEntity userRole) {
        this.userRoles.add(userRole);
    }

}
