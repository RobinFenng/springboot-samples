package com.moregx;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({
	"classpath:property-source.properties"
})
public class Application {

	public static void main(String[] args) {
		
		SpringApplication application = new SpringApplication(Application.class);
		Map<String,Object> defaultProperties = new HashMap<String,Object>();
		defaultProperties.put("name", "name in application.setDefaultProperties level 17");
		application.setDefaultProperties(defaultProperties);
		application.run(args);
		
		//SpringApplication.run(Application.class, args);
	}
}
