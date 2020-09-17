package ru.jpixel.personaldiaryauthorizationservice.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtInfo {
    @Value("${security.jwt.uri:/auth/**}")
    private String uri;

    @Value("${security.jwt.name:access_token}")
    private String accessCookieName;

    @Value("${security.jwt.claim.authorities:authorities}")
    private String claimAuthorities;

    @Value("${security.jwt.name:user_id}")
    private String claimUserId;

    @Value("${security.jwt.name:diary_id}")
    private String claimDiaryId;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;
}
