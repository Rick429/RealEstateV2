package com.salesianostriana.dam.realestatev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RealEstatev2Application {

	public static void main(String[] args) {
		SpringApplication.run(RealEstatev2Application.class, args);
	}

}
