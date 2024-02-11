package com.exercise.fx.stream.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FXMarketDataStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FXMarketDataStreamApplication.class, args);
	}

}
