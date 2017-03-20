package com.moregx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("getData")
	String getData() {
		logger.info("ss:{}","ss");
		logger.warn("warn:{}","warn");
		return "getData";
	}
}
