package com.kayadhu.authUsernamePass.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kayadhu.authUsernamePass.models.Users;

@Service
public interface CustomUserDetailsService extends UserDetailsService {
	public String registerUser(Users registerDto);

}
