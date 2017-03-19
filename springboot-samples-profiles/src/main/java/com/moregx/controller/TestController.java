package com.moregx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.bean.Apple;
import com.moregx.bean.Banana;

@RestController
@RequestMapping("test")
public class TestController {

	
	@Autowired
	Apple apple;
	
	@Autowired
	Banana banana;
	
	@RequestMapping("getApple")
	Apple getApple(){
		return apple;
	}
	
	@RequestMapping("getBanana")
	Banana getBanana(){
		return banana;
	}
}
