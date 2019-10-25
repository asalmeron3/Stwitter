package com.trilogy.stwitterconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class StwitterConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StwitterConfigServerApplication.class, args);
	}

}
