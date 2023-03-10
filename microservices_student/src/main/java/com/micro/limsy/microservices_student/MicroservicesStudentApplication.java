package com.micro.limsy.microservices_student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicesStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesStudentApplication.class, args);
	}

}
