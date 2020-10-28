package ru.jpixel.models.dtos.open;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DirectoryDto {
    @ApiModelProperty(notes = "Идентификатор тега")
    public Long id;
    @ApiModelProperty(notes = "Наименование тега на русском")
    public String nameRu;
    @ApiModelProperty(notes = "Наименование тега на английском")
    public String nameEn;
    @ApiModelProperty(notes = "Код тега")
    public String code;
}
