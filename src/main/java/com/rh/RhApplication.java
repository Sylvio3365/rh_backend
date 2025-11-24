package com.rh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.rh.controller", "com.rh.service"})
@EntityScan(basePackages = "com.rh.model")
@EnableJpaRepositories(basePackages = "com.rh.repository")
public class RhApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RhApplication.class, args);
	}
}