package ru.jpixel.personaldiaryauthorizationservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class PersonalDiaryUser extends User {

    private final String id;
    private final String diaryId;

    public PersonalDiaryUser(String id, String username, String password, String diaryId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.diaryId = diaryId;
    }

    public PersonalDiaryUser(String id, String username, String password, String diaryId, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.diaryId = diaryId;
    }

    public String getId() {
        return id;
    }

    public String getDiaryId() {
        return diaryId;
    }
}
