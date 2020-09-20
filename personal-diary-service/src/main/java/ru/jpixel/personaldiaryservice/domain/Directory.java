package ru.jpixel.personaldiaryservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Directory {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "NAME_RU", nullable = false)
    private String nameRu;

    @Column(name = "NAME_EN", nullable = false)
    private String nameEn;

    @Column(name = "CODE", nullable = false)
    private String code;
}
