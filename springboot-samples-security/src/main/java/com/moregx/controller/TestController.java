package com.moregx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	
	
	@RequestMapping("getData")
	String getData(){
		
		return "success21";
	}
}
