package com.adde.adai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AdaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdaiApplication.class, args);
	}

}
