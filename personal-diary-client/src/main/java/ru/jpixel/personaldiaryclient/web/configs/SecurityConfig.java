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
import ru.jpixel.personaldiaryclient.web.security.JwtAuthenticationEntryPoint;
import ru.jpixel.personaldiaryclient.web.security.JwtInfo;
import ru.jpixel.personaldiaryclient.web.security.JwtTokenAuthenticationFilter;

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
