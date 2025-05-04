package com.irex.UniversityConfigServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class UniversityConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityConfigServerApplication.class, args);
	}

}
