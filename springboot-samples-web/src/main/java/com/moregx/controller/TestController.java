package com.moregx.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.model.Apple;

@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/getData")
	public String getApple(Model model) {
		Apple apple = new Apple();
		apple.setColor("red");
		apple.setName("apple");
		model.addAttribute("apple", apple);
		return "apple";

	}

	

}
