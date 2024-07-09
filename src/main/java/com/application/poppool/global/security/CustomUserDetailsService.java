package com.application.poppool.global.security;

import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        return new User(
                user.getUserId(),
                password,
                authorities
        );
    }

    /**
     * 임시 토큰으로 들어온 사용자 같은 경우에는 실제로 DB에 데이터가 없기 때문에 검증하지 않고 객체만 반환해줌
     * @param userId
     * @return
     */
    public UserDetails loadUserByTemporaryToken(String userId) {
        return new User(userId, "", new ArrayList<>());
    }

}
