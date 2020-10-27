package ru.jpixel.personaldiaryclient.web.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SpringfoxConfig {

    @Autowired
    private final ZuulProperties properties;

    private final String VERSION_API = "1.0";

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.jpixel.personaldiaryclient.web.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            var resources = new ArrayList<SwaggerResource>();
            properties.getRoutes().values()
                    .forEach(route -> resources.add(createResource(route.getId(), false)));
            resources.add(createResource("gateway-api", true));
            return resources;
        };
    }

    private SwaggerResource createResource(String location, boolean isDefault) {
        var swaggerResource = new SwaggerResource();
        if (isDefault) {
            swaggerResource.setName(location);
            swaggerResource.setLocation("/v2/api-docs");
            swaggerResource.setSwaggerVersion(VERSION_API);
        } else {
            var locationReplace = location.replaceAll("/\\**", "");
            if ("auth".equals(locationReplace)) {
                swaggerResource.setName(locationReplace);
                swaggerResource.setLocation("/" + locationReplace + "/v2/api-docs");
                swaggerResource.setSwaggerVersion(VERSION_API);
            }
        }
        return swaggerResource;
    }
}
