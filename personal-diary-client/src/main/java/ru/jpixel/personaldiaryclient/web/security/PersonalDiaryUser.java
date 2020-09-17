package ru.jpixel.personaldiaryclient.web.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class PersonalDiaryUser {
    private final Long id;
    private final Long diaryId;
    private final String username;
    private final List<String> roles;
}
