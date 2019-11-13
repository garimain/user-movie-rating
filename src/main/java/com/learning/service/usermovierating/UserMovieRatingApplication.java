package com.learning.service.usermovierating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserMovieRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMovieRatingApplication.class, args);
	}

}
