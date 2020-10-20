package ru.jpixel.personaldiaryuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonalDiaryUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalDiaryUserServiceApplication.class, args);
	}

}
