package ru.jpixel.personaldiaryservice.services;

import org.springframework.core.convert.converter.Converter;
import ru.jpixel.models.dtos.open.DirectoryDto;
import ru.jpixel.personaldiaryservice.domain.Directory;

public class DirectoryConverter implements Converter<Directory, DirectoryDto> {
    @Override
    public DirectoryDto convert(Directory directory) {
        var result = new DirectoryDto();
        result.setId(directory.getId());
        result.setCode(directory.getCode());
        result.setNameEn(directory.getNameEn());
        result.setNameRu(directory.getNameRu());
        return result;
    }
}
