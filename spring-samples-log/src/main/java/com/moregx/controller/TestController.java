package com.moregx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TestService service;
	
	@RequestMapping("getData")
	String getData() {
		logger.info("controller info :{}","info");
		logger.warn("controller warn:{}","warn");
		return service.getData();
	}
}
