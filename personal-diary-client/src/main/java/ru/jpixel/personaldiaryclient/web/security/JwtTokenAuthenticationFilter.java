package ru.jpixel.personaldiaryclient.web.security;

import io.jsonwebtoken.Jwts;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtInfo jwtInfo;

    public JwtTokenAuthenticationFilter(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        var token = tokenSearchInCookies(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        try {
            var claims = Jwts.parser()
                    .setSigningKey(jwtInfo.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            var username = claims.getSubject();
            if (username != null) {
                var claimsAuthorities = claims.get(jwtInfo.getClaimAuthorities(), ArrayList.class);
                var stringAuthorities = new ArrayList<String>(claimsAuthorities.size());
                //noinspection unchecked
                claimsAuthorities.forEach(o -> stringAuthorities.add((String) o));
                List<GrantedAuthority> authorities = stringAuthorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                var userId = Long.valueOf(claims.get(jwtInfo.getClaimUserId(), String.class));
                var diaryId = !claims.get(jwtInfo.getClaimDiaryId(), String.class).equals("null")
                        ? Long.valueOf(claims.get(jwtInfo.getClaimDiaryId(), String.class))
                        : null;
                var user = new PersonalDiaryUser(userId, diaryId, username, stringAuthorities);
                var auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    private String tokenSearchInCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return tokenSearchInHeaders(request);
        }

        var findCookie = Stream.of(request.getCookies())
                .filter(cookie -> jwtInfo.getAccessCookieName().equals(cookie.getName()))
                .findFirst().orElse(null);

        if (findCookie == null) {
            return null;
        }

        return findCookie.getValue();
    }

    private String tokenSearchInHeaders(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        } else {
            return null;
        }
    }
}
