package ru.jpixel.peronaldiaryswaggerstarter;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import ru.jpixel.peronaldiaryswaggerstarter.autoconfig.PersonalDiarySwaggerAutoConfiguration;
import ru.jpixel.peronaldiaryswaggerstarter.autoconfig.PersonalDiarySwaggerDocketProperties;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.autoconfigure.AutoConfigurations.of;

public class PersonalDiarySwaggerAutoConfigurationIT {
    @Test
    void shouldAutoConfigurationBeAppliedToWebApplication() {
        new WebApplicationContextRunner()
                .withConfiguration(of(PersonalDiarySwaggerAutoConfiguration.class))
                .withPropertyValues(
                        "personal.diary.swagger.docket.basePackage=ru.text.package",
                        "personal.diary.swagger.docket.paths=/api/v1/*,/api/v2/*"
                )
                .run(context -> assertThat(context).hasNotFailed()
                        .hasSingleBean(Docket.class)
                        .hasSingleBean(PersonalDiarySwaggerDocketProperties.class)
                        .getBean(PersonalDiarySwaggerDocketProperties.class).hasNoNullFieldsOrProperties());
    }
}
