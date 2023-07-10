package com.kayadhu.authUsernamePass.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kayadhu.authUsernamePass.models.Users;
import com.kayadhu.authUsernamePass.repository.UsersRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private UsersRepository userRepo;

	@GetMapping
	public String displayDashboard(Model model) {
		logger.info("Dashboard controller processing...");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetails userDet = (UserDetails) securityContext.getAuthentication().getPrincipal();
		Users users = userRepo.findByUsername(userDet.getUsername());

		if (userDet.isAccountNonLocked()) {
			model.addAttribute("userDetails", users.getName());
			logger.info("dashboard displayed");
			return "dashboard";
		} else {
			logger.info("dashboard cound not be displayed as account is locked");
			return "redirect:/login?error";
		}

	}

}
