package com.kayadhu.authUsernamePass.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kayadhu.authUsernamePass.models.Users;
import com.kayadhu.authUsernamePass.service.CustomUserDetailsService;

@Controller
@RequestMapping("/login")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private CustomUserDetailsService userService;

	@ModelAttribute("user")
	public Users loginDto() {
		return new Users();
	}

	@GetMapping
	public String login() {
		logger.info("Login get method");
		return "login";
	}

	@PostMapping
	public ResponseEntity<?> loginUser(@ModelAttribute("user") Users loginDto) {
		logger.info("login post method");
		return ResponseEntity.ok(userService.loadUserByUsername(loginDto.getUsername()));
	}
}
