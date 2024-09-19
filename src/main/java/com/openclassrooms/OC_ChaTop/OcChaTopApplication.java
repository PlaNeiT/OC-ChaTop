package com.openclassrooms.OC_ChaTop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OcChaTopApplication {
	public static void main(String[] args) {
		SpringApplication.run(OcChaTopApplication.class, args);
	}
}
