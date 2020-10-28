package ru.jpixel.peronaldiaryswaggerstarter.autoconfig;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@ConfigurationPropertiesScan("ru.jpixel.peronaldiaryswaggerstarter")
@EnableSwagger2
@ConditionalOnProperty(value = "personal.diary.swagger.enabled", havingValue = "true", matchIfMissing = true)
public class PersonalDiarySwaggerAutoConfiguration {

    @Autowired
    private PersonalDiarySwaggerDocketProperties personalDiarySwaggerDocketProperties;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiHeaderKey()))
                .select()
                .apis(getApis())
                .paths(getPaths())
                .build()
                .apiInfo(apiInfo());
    }

    @SuppressWarnings("Guava")
    private Predicate<RequestHandler> getApis() {
        return personalDiarySwaggerDocketProperties.getBasePackage() != null
                ? RequestHandlerSelectors.basePackage(personalDiarySwaggerDocketProperties.getBasePackage())
                : RequestHandlerSelectors.any();
    }

    @SuppressWarnings("Guava")
    private Predicate<String> getPaths() {
        var paths = personalDiarySwaggerDocketProperties.getPaths();
        if (CollectionUtils.isEmpty(paths)) {
            return PathSelectors.any();
        } else if (paths.size() == 1) {
            return regex(paths.get(0));
        } else {
            return or(paths.stream().map(PathSelectors::regex).collect(Collectors.toList()));
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Spring Boot REST API",
                "Spring Boot REST API for Personal diary application",
                "1.0", null,
                new Contact("jpixel40", null, "jpixel40@gmail.com"),
                null, null, Collections.emptyList());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        var authorizationScopeList = List.of(new AuthorizationScope("global", "accessEverything"));
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopeList.toArray(AuthorizationScope[]::new)));
    }

    private ApiKey apiHeaderKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}
