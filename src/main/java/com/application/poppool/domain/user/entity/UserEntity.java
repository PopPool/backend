package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.auth.enums.SocialType;
import com.application.poppool.domain.user.enums.Gender;
import com.application.poppool.domain.user.enums.Role;
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
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "USER_ID", unique = true)
    @NotBlank // 애플리케이션 레벨에서 유효성 검사
    private String userId; // 회원 ID

    @Column(name = "PASSWORD")
    @Builder.Default
    private String password = ""; // Default 값으로 빈 문자열 설정

    @Column(name = "NICKNAME")
    private String nickName; // 닉네임

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    @Column(name = "AGE")
    private Integer age; // 연령

    @Column(name = "TERM_AGREE_YN")
    private String termAgreeYn; // 약관동의 여부

    @Column(name = "SOCIAL_TYPE")
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserInterestEntity> userInterestEntities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    // 닉네임 변경
    public void updateNickname(String nickName) {
        this.nickName = nickName;
    }


    // 유저의 관심사 추가
    public void addInterest(UserInterestEntity userInterestEntity) {
        userInterestEntities.add(userInterestEntity);
    }


    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
