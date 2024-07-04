package com.application.poppool.global.security;

import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        // 비밀번호가 null인 경우 빈 문자열로 대체, 인증을 하기 위함
        String password = user.getPassword() != null ? user.getPassword() : "";

        // 카카오 사용자 정보에서 권한을 설정
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // UserDetails 객체를 생성
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                password,
                authorities
        );
    }
}
