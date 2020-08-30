package ru.jpixel.personaldiaryuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PersonalDiaryUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalDiaryUserServiceApplication.class, args);
	}

}
