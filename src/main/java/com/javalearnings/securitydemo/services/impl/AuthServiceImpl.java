package com.javalearnings.securitydemo.services.impl;

import com.javalearnings.securitydemo.entities.auth.User;
import com.javalearnings.securitydemo.filters.JwtTokenUtil;
import com.javalearnings.securitydemo.model.auth.AuthResponse;
import com.javalearnings.securitydemo.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtTokenUtil jwtUtil;

	@Override
	public AuthResponse generateTokenUsingUsername(String username, String password) {
		try {
			log.debug("requestLoginForm username {}", username);
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							username, password)
			);
			
			User user = (User) authentication.getPrincipal();
			log.debug("User {}", user);
			String accessToken = jwtUtil.generateAccessToken(user);
			return new AuthResponse(user.getUsername(), accessToken);

		} catch (BadCredentialsException ex) {
			return null;
		}
	}

}
