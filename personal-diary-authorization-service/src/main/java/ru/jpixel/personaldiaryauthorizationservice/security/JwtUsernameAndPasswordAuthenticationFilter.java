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
import ru.jpixel.models.security.UserCredentials;

import javax.servlet.FilterChain;
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
            UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                    credentials.getPassword(), Collections.emptyList());
            return authManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("user_id", ((PersonalDiaryUser) auth.getPrincipal()).getId())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtInfo.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtInfo.getSecret().getBytes())
                .compact();
        response.addHeader(jwtInfo.getHeader(), jwtInfo.getPrefix() + token);
    }
}
