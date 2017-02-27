package com.moregx.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.bean.User;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	User user;
	
	@RequestMapping("getName")
	String getName(){
		
		return user.getName();
	}
}
