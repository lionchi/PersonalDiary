package ru.jpixel.models.dtos.open;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DirectoryDto {
    public Long id;
    public String nameRu;
    public String nameEn;
    public String code;
}
