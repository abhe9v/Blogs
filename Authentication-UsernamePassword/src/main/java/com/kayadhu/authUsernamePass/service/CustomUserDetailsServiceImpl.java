package com.kayadhu.authUsernamePass.service;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kayadhu.authUsernamePass.models.Users;
import com.kayadhu.authUsernamePass.repository.UsersRepository;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);
	@Autowired
	private UsersRepository usersRepository;
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername service processing...");
		Users user = usersRepository.findByUsername(username);
		if (user == null) {
			log.info("User not found");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		log.info("user found returning userdetails object");
		return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
	}

	@Override
	public String registerUser(Users registerDto) {

		log.info("registerUser service processing...");
		Users user1 = usersRepository.findByUsername(registerDto.getUsername());
		if (user1 == null) {
			log.info("User not found, Creating new User...");
			Users user = new Users();
			user.setName(registerDto.getName());
			user.setUsername(registerDto.getUsername());
			user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
			log.info("new user created saving now...");
			usersRepository.save(user);
			return "login";
			
		} else {
			log.info("Duplicate username. throwomg error...");
			return "register?duplicate";
		}

	}

}
