package com.moregx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moregx.model.Apple;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/getData1")
	public String getApple(Model model) {
		Apple apple = new Apple();
		apple.setColor("red");
		apple.setName("apple");
		model.addAttribute("apple", apple);
		return "pdfViewResolver";

	}

	

}
