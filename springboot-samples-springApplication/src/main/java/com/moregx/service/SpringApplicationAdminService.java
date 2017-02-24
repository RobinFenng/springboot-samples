package com.moregx.service;

import org.springframework.boot.admin.SpringApplicationAdminMXBean;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationAdminService implements SpringApplicationAdminMXBean {

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmbeddedWebApplication() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public void shutdown() {

	}

}
