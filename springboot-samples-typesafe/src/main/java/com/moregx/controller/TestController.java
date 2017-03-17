package com.moregx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.model.FooProperties;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	FooProperties fooProperties;
	
	@RequestMapping("/getData")
	String getData(){
		
		return fooProperties.getSecurity().getUsername();
	}

}
