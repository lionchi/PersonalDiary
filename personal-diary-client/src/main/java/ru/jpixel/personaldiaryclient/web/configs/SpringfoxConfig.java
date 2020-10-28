package ru.jpixel.personaldiaryclient.web.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class SpringfoxConfig {

    @Autowired
    private final ZuulProperties properties;

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            var resources = new ArrayList<SwaggerResource>();
            for (ZuulProperties.ZuulRoute route : properties.getRoutes().values()) {
                if (!route.getPath().contains("auth")) {
                    resources.add(createResource(route.getPath(), false));
                }
            }
            resources.add(createResource("gateway-api", true));
            return resources;
        };
    }

    private SwaggerResource createResource(String location, boolean isDefault) {
        var swaggerResource = new SwaggerResource();
        if (isDefault) {
            swaggerResource.setName(location);
            swaggerResource.setLocation("/v2/api-docs");
        } else {
            var locationReplace = location.replaceAll("/\\**", "");
            swaggerResource.setName(locationReplace);
            swaggerResource.setLocation("/" + locationReplace + "/v2/api-docs");
        }
        String VERSION_API = "1.0";
        swaggerResource.setSwaggerVersion(VERSION_API);
        return swaggerResource;
    }
}
