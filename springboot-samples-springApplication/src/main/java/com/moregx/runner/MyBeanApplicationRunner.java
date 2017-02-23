package com.moregx.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//@Order(3)
public class MyBeanApplicationRunner implements ApplicationRunner ,Ordered{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("ApplicationRunner 33");
	}

	@Override
	public int getOrder() {
		return 3;
	}

}
