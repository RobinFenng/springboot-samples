package com.moregx.runner;

import org.springframework.core.Ordered;

public class CustomerOrder implements Ordered{

	@Override
	public int getOrder() {
		return 0;
	}

}
