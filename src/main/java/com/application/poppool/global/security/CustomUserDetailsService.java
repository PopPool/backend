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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        // 비밀번호가 null인 경우 빈 문자열로 대체, 인증을 하기 위함
        String password = user.getPassword() != null ? user.getPassword() : "";

        // DB에 저장된 사용자 권한 가져옴
        Set<GrantedAuthority> authorities = user.getUserRoles()
                .stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getUserRole()))
                .collect(Collectors.toSet());

        return new User(
                user.getUserId(),
                password,
                authorities
        );
    }

    /**
     * 임시 토큰으로 들어온 사용자 같은 경우에는 실제로 DB에 데이터가 없기 때문에 검증하지 않고 객체만 반환해줌
     *
     * @param userId
     * @return
     */
    public UserDetails loadUserByTemporaryToken(String userId) {
        return new User(userId, "", new ArrayList<>());
    }

}
