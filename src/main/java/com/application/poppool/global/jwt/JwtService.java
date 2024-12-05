package com.application.poppool.global.jwt;

import com.application.poppool.domain.auth.dto.response.LoginResponse;
import com.application.poppool.domain.auth.dto.response.TokenResponse;
import com.application.poppool.domain.auth.enums.TokenType;
import com.application.poppool.domain.token.service.RefreshTokenService;
import com.application.poppool.global.security.CustomUserDetailsService;
import com.application.poppool.global.utils.TimeUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtService {

    private final Key key;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtService(CustomUserDetailsService customUserDetailsService, JwtProperties jwtProperties, RefreshTokenService refreshTokenService) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtProperties = jwtProperties;
        this.refreshTokenService = refreshTokenService;
    }

    public LoginResponse createJwtToken(String userId, boolean isTemporary) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime accessTokenExpiresAt = now.plus(jwtProperties.getToken().getAccess().getExpiration(), ChronoUnit.MILLIS);
        LocalDateTime refreshTokenExpiresAt = now.plus(jwtProperties.getToken().getRefresh().getExpiration(), ChronoUnit.MILLIS);

        // LocalDateTime을 Date로 변환
        Date accessTokenExpiresDate = Date.from(accessTokenExpiresAt.atZone(ZoneId.systemDefault()).toInstant());
        Date refreshTokenExpiresDate = Date.from(refreshTokenExpiresAt.atZone(ZoneId.systemDefault()).toInstant());


        // Access Token 생성
        String accessToken = createAccessToken(userId, isTemporary, accessTokenExpiresDate);
        // Refresh Token 생성
        String refreshToken = createRefreshToken(userId, isTemporary, refreshTokenExpiresDate);

        return LoginResponse.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresAt(accessTokenExpiresAt)
                .refreshTokenExpiresAt(refreshTokenExpiresAt)
                .build();
    }

    public Map<String, Object> setClaims(String userId, boolean isTemporary) {
        // 클레임 설정
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);               // 사용자 ID
        claims.put("isTemporary", isTemporary);     // 임시 여부
        return claims;
    }

    public String createAccessToken(String userId, boolean isTemporary, Date expirationDate) {

        // 클레임 설정
        Map<String, Object> claims = this.setClaims(userId, isTemporary);

        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationDate)          // 토큰 만료 시간
                .addClaims(claims)                      // 클레임 추가
                .signWith(key)
                .compact();

    }

    public String createRefreshToken(String userId, boolean isTemporary, Date expirationTime) {
        // 클레임 설정
        Map<String, Object> claims = this.setClaims(userId, isTemporary);

        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .addClaims(claims)                      // 클레임 추가     // 클레임 설정
                .signWith(key)
                .compact();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드


    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            log.info("Invalid JWT Token", e);
            return false;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            return false;
        } catch (IllegalArgumentException | DecodingException e) {
            log.info("JWT claims string is empty.", e);
            return false;
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(jwtProperties.getToken().getHeader());

        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            if (authorizationHeader.startsWith("Bearer") && authorizationHeader.length() > 7) {
                String accessToken = authorizationHeader.substring(7); // accesstoken 추출
                return accessToken;
            }
        }

        return null; // 헤더 비어있으면 null 리턴
    }

    // Authenticaiton 가져오기
    public Authentication getAuthentication(String accessToken, boolean isTemporary) {
        String userId = this.getUserId(accessToken);

        UserDetails userDetails = isTemporary
                ? customUserDetailsService.loadUserByTemporaryToken(userId) // 임시 토큰인 경우
                : customUserDetailsService.loadUserByUsername(userId); // 정식 토큰인 경우

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 userId 추출
    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", String.class);
    }


    // 토큰에서 임시토큰여부 추출
    public boolean getIsTemporary(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("isTemporary", Boolean.class);
    }

    public LocalDateTime getExpiration(String token) {
        // accessToken 남은 유효시간
        Date expirationDate = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();

        // Date -> LocalDateTime 변환
        return TimeUtils.convertDateToLocalDateTime(expirationDate);
    }

    // access 토큰 여부
    public boolean isAccessToken(String token) {
        Claims claims = parseClaims(token);
        String subject = claims.getSubject();
        return TokenType.ACCESS.name().equals(subject);
    }


    // refresh 토큰 여부
    public boolean isRefreshToken(String token) {
        Claims claims = parseClaims(token);
        String subject = claims.getSubject();
        return TokenType.REFRESH.name().equals(subject);
    }


    // 응답 헤더에 accessToken 세팅
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer " + accessToken);
    }

    // 응답 헤더에 refreshToken 세팅
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Authorization-refresh", "Bearer " + refreshToken);
    }

    /**
     * 일단 사용 X
     * public boolean isTokenBlacklisted(String refreshToken) {
     * return blackListTokenService.isTokenBlacklisted(refreshToken);
     * }
     */

    public boolean isUserRefreshTokenValid(String userId, String refreshToken) {
        return refreshTokenService.isUserRefreshTokenValid(userId, refreshToken);
    }

    public void saveOrReplaceRefreshToken(String userId, String refreshToken, LocalDateTime expiresAt) {
        refreshTokenService.saveOrReplaceRefreshToken(userId, refreshToken, expiresAt);
    }

    public TokenResponse reIssueToken(String refreshToken, HttpServletResponse response) {
        String userId = getUserId(refreshToken);
        boolean isTemporary = getIsTemporary(refreshToken);

        LoginResponse loginResponse = createJwtToken(userId, isTemporary); // AT,RT 재생성

        // 헤더
        setHeaderAccessToken(response, loginResponse.getAccessToken()); // AT 발급
        setHeaderRefreshToken(response, loginResponse.getRefreshToken()); // RT 발급

        saveOrReplaceRefreshToken(userId, loginResponse.getRefreshToken(), loginResponse.getRefreshTokenExpiresAt()); // RT 테이블에 새로운 RT로 기존 RT 대체

        return TokenResponse.builder()
                .accessToken(loginResponse.getAccessToken())
                .refreshToken(loginResponse.getRefreshToken())
                .accessTokenExpiresAt(loginResponse.getAccessTokenExpiresAt())
                .refreshTokenExpiresAt(loginResponse.getRefreshTokenExpiresAt())
                .build();
    }

}
