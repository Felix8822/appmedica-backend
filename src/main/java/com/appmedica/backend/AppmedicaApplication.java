package com.appmedica.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.appmedica.backend.model")
@ComponentScan(basePackages = "com.appmedica.backend")
@EnableScheduling
public class AppmedicaApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppmedicaApplication.class, args);
	}
}
