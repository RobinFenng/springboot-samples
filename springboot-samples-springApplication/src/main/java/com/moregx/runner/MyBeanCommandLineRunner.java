package com.moregx.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//@Order(2)
public class MyBeanCommandLineRunner implements CommandLineRunner,Ordered{

	@Override
	public void run(String... args) throws Exception {
			System.out.println("MyBeanCommandLineRunne 2222");
	}

	@Override
	public int getOrder() {
		return 2;
	}

}
