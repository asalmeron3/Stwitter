package com.trilogy.stwitterpostservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class StwitterPostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StwitterPostServiceApplication.class, args);
	}

}
