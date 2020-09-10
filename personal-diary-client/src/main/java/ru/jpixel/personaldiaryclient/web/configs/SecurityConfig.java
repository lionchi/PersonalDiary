package ru.jpixel.personaldiaryclient.web.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import ru.jpixel.personaldiaryclient.web.security.JwtAuthenticationEntryPoint;
import ru.jpixel.personaldiaryclient.web.security.JwtInfo;
import ru.jpixel.personaldiaryclient.web.security.JwtTokenAuthenticationFilter;

import javax.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtInfo jwtInfo;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    Cookie cookie = new Cookie(jwtInfo.getAccessCookieName(), null);
                    cookie.setPath("/api");
                    cookie.setMaxAge(0);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                    new HttpStatusReturningLogoutSuccessHandler().onLogoutSuccess(request, response, authentication);
                })
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtInfo), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/registration-api/**").permitAll()
                .antMatchers(jwtInfo.getUri()).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers(HttpMethod.GET, "/*.css", "/*.png", "/*.ico", "/*.js", "/*.json")
                .antMatchers(HttpMethod.GET, "/login", "/registration")
                .antMatchers(HttpMethod.GET, "/index.html", "/static/**");
    }
}
