package ru.jpixel.peronaldiaryswaggerstarter.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties("personal.diary.swagger.docket")
@ConstructorBinding
public class PersonalDiarySwaggerDocketProperties {

    /**
     * Пакет для сканирования api
     */
    private final String basePackage;

    /**
     * Список api путей для выборки swagger
     * Например: /api/, /api/v1/users
     */
    private final List<String> paths;

    public PersonalDiarySwaggerDocketProperties(String basePackage, List<String> paths) {
        this.basePackage = basePackage;
        this.paths = paths;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public List<String> getPaths() {
        return paths != null ? List.copyOf(paths) : null;
    }
}
