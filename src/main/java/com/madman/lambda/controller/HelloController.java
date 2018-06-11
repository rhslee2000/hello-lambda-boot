package com.madman.lambda.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.madman.lambda.dto.GreetingDto;

@EnableWebMvc
@RestController
public class HelloController {

	@RequestMapping(path = "/greeting", method = RequestMethod.GET)
	public GreetingDto sayHello(@RequestParam String name) {
		String message = "Hello " + name;
		GreetingDto dto = new GreetingDto();
		dto.setMessage(message);
		return dto;
	}
}
