package com.moregx;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({
	"classpath:property-source.properties"
})
@ServletComponentScan
public class Application  {

	
	public static void main(String[] args) {
		
		
		System.setProperty("name", "name in Java System properties  level 9");
		SpringApplication application = new SpringApplication(Application.class);
		Map<String,Object> defaultProperties = new HashMap<String,Object>();
		defaultProperties.put("name", "name in application.setDefaultProperties level 17");
		application.setDefaultProperties(defaultProperties);
		application.run(args);
		
		//SpringApplication.run(Application.class, args);
	}
}
