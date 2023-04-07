package com.micro.limsy.microservices_issuedbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicesIssuedbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesIssuedbookApplication.class, args);
	}

	@Bean
	@LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
