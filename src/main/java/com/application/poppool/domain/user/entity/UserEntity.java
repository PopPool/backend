package com.application.poppool.domain.user.entity;

import com.application.poppool.domain.auth.SocialType;
import com.application.poppool.domain.user.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private String password = ""; // Default 값으로 빈 문자열 설정

    @Column(name = "NICKNAME")
    private String nickName; // 닉네임

    @Column(name = "GENDER")
    private String gender; // 성별

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "TERM_AGREE_YN")
    private String termAgreeYn; // 약관동의 여부

    @Column(name = "SOCIAL_TYPE")
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    // 닉네임 변경
    public void updateNickname(String nickName){
        this.nickName = nickName;
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
