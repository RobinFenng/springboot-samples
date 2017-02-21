package com.moregx;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * 
 * @Description: 此为主类，不建议放到src根目录下.
 * @author: Robin github: https://github.com/RobinFenng
 * @date: 2017年1月3日 下午3:30:42
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
// @Import(Bean.class)
//@ImportResource("application.xml")
public class Application {



	public static void main(String[] args) {

		// @import
		// AnnotationConfigApplicationContext context = new
		// AnnotationConfigApplicationContext("com.robin");
		// Bean bean = context.getBean(Bean.class);
		// bean.print();

		 //@ImportResource
//		 ApplicationContext context = new  AnnotationConfigApplicationContext("com.moregx");
//		 Bean bean = context.getBean(Bean.class);
//		 bean.print();
		
		//获取自动配置类列表
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class,Application.class.getClassLoader());
		
		for(String s: configurations){
			System.out.println(s);
		}

		// SpringApplication.run(Application.class, args);
	}

}
