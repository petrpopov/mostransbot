package com.innerman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MostransbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MostransbotApplication.class, args);
	}
}
