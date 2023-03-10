package com.micro.limsy.microservices_librarian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicesLibrarianApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesLibrarianApplication.class, args);
	}

}
