package com.example.authapi.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authManager;

	public AuthServiceImpl(final AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	@Override
	public UserResponseDTO login(LoginDTO loginDTO) {
		var authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		if(!authentication.isAuthenticated()) {
			// throw new Exception("fails");
		}
		
		return null;
	}

	@Override
	public void logout(String token) {
		// TODO Auto-generated method stub

	}
}
