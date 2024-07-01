package com.application.poppool.domain.token.service;


import com.application.poppool.domain.token.entity.BlackListTokenEntity;
import com.application.poppool.domain.token.repository.BlackListTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlackListTokenService {

    private final BlackListTokenRepository blackListTokenRepository;

    @Transactional
    public void addTokenToBlackList(String accessToken, LocalDateTime expiryDateTime) {
        BlackListTokenEntity blackListToken = BlackListTokenEntity.builder()
                .token(accessToken)
                .expiryDateTime(expiryDateTime)
                .build();

        
        // 토큰 블랙리스트 추가
        blackListTokenRepository.save(blackListToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackListTokenRepository.existsByToken(token);
    }





}
