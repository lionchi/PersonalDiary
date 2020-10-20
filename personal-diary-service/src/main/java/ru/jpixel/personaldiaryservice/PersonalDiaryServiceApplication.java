package ru.jpixel.personaldiaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class PersonalDiaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalDiaryServiceApplication.class, args);
	}

}
