package com.moregx;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class Application {
	
	
	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
	}

}
