package ru.jpixel.personaldiaryauthorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jpixel.personaldiaryauthorizationservice.security.PersonalDiaryUser;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalDiaryUserDetailsService implements UserDetailsService {

    private final UserServiceFeignClient userServiceFeignClient;

    private static final String PREFIX_ROLE = "ROLE_";

    @Override
    public PersonalDiaryUser loadUserByUsername(String username) throws UsernameNotFoundException {

        var foundUser = userServiceFeignClient.findByLogin(username);

        if (foundUser == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        var authorities = foundUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role))
                .collect(Collectors.toList());

        return new PersonalDiaryUser(String.valueOf(foundUser.getId()), foundUser.getLogin(), foundUser.getPassword(), authorities);
    }
}
