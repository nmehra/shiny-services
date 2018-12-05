package com.example.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import com.gremlin.ApplicationCoordinates;
import com.gremlin.GremlinCoordinatesProvider;
import com.gremlin.GremlinService;
import com.gremlin.GremlinServiceFactory;

@EnableCircuitBreaker
@SpringBootApplication
public class BankingServiceApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(BankingServiceApplication.class, args);
	}
}
