package com.apmm.LocationProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {
		"com.apmm"})
public class LocationProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationProducerApplication.class, args);
	}

}
