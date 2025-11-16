package com.rh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.controller", "com.service"})
@EntityScan(basePackages = "com.model")
@EnableJpaRepositories(basePackages = "com.repository")
public class RhApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RhApplication.class, args);
	}
}