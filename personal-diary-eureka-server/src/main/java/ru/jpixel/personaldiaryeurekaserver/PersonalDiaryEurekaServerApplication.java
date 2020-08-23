package ru.jpixel.personaldiaryeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PersonalDiaryEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalDiaryEurekaServerApplication.class, args);
    }

}
