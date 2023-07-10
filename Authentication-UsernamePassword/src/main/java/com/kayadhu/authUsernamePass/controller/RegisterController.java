package com.kayadhu.authUsernamePass.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kayadhu.authUsernamePass.models.Users;
import com.kayadhu.authUsernamePass.service.CustomUserDetailsService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private CustomUserDetailsService userService;

	@ModelAttribute("user")
	private Users user() {
		return new Users();
	}

	@GetMapping
	public String register() {
		logger.info("Register get method");
		return "register";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") Users registerDto) {
		logger.info("Register post method");
		String url = userService.registerUser(registerDto);
		return "redirect:/" + url;
	}
}
