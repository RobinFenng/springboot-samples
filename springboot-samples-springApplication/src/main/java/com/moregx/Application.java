package com.moregx;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		 SpringApplication.run(Application.class, args);
		
		//自定义SpringApplication
//		SpringApplication app = new SpringApplication(Application.class);
//		app.setBannerMode(Banner.Mode.OFF);
//		app.run(args);
		 
		// MBeanServer mBeanServer =  MBeanServerFactory.createMBeanServer();
		
	}

}
