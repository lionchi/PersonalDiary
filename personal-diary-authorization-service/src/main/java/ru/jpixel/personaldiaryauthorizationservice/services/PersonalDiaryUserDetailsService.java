package ru.jpixel.personaldiaryauthorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jpixel.models.dtos.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalDiaryUserDetailsService implements UserDetailsService {

    private final UserServiceFeignClient userServiceFeignClient;

    private static final String PREFIX_ROLE = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto foundUser = userServiceFeignClient.findByLogin(username);

        if (foundUser == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        List<GrantedAuthority> authorities = foundUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role))
                .collect(Collectors.toList());

        return new User(foundUser.getLogin(), foundUser.getPassword(), authorities);
    }
}
