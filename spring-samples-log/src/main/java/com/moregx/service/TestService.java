package com.moregx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getData(){
		logger.debug("serivce debug:{}","getData");
		logger.info("serivce info:{}","getData");
		logger.warn("serivce warn:{}","getData");
		logger.error("serivce error:{}","getData");
		return "got !!";
	}

}
