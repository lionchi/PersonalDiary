package ru.jpixel.personaldiaryuserservice.services;

import org.springframework.core.convert.converter.Converter;
import ru.jpixel.models.dtos.secr.ShortUserDto;
import ru.jpixel.personaldiaryuserservice.domain.secr.User;

public class ShortUserConverter implements Converter<User, ShortUserDto> {
    @Override
    public ShortUserDto convert(User user) {
        var shorUserDto = new ShortUserDto();
        shorUserDto.setName(user.getName());
        shorUserDto.setEmail(user.getEmail());
        return shorUserDto;
    }
}
