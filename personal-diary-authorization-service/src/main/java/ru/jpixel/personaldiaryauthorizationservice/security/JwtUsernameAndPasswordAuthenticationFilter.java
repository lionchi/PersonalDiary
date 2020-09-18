package ru.jpixel.personaldiaryauthorizationservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.jpixel.models.dtos.secr.UserCredentials;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authManager;
    private final JwtInfo jwtInfo;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtInfo jwtInfo) {
        this.authManager = authManager;
        this.jwtInfo = jwtInfo;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtInfo.getUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            var authToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                    credentials.getPassword(), Collections.emptyList());
            return authManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        var now = System.currentTimeMillis();
        var token = Jwts.builder()
                .setSubject(auth.getName())
                .claim(jwtInfo.getClaimAuthorities(), auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim(jwtInfo.getClaimUserId(), ((PersonalDiaryUser) auth.getPrincipal()).getId())
                .claim(jwtInfo.getClaimDiaryId(), ((PersonalDiaryUser) auth.getPrincipal()).getDiaryId())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtInfo.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtInfo.getSecret().getBytes())
                .compact();
        var cookie = new Cookie(jwtInfo.getAccessCookieName(), token);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");
        response.addCookie(cookie);
    }
}
