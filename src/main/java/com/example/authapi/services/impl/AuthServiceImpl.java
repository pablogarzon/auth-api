package com.example.authapi.services.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.mappers.UserMapper;
import com.example.authapi.models.User;
import com.example.authapi.repositories.UserRepository;
import com.example.authapi.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthServiceImpl(final UserRepository userRepository, final JWTService jwtService, final AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public UserResponseDTO login(LoginDTO loginDTO) {
		var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		var updatedUser = updateLastLogin(authentication);
		
		var response = UserMapper.toDto(updatedUser);
		response.setToken(jwtService.generateToken(updatedUser));

		return response;
	}

	private User updateLastLogin(final Authentication authentication) {
		User user = (User) authentication;
		user.setLastLogin(LocalDateTime.now());
		return userRepository.save(user);
	}

	@Override
	public void logout(String token) {
		// TODO Auto-generated method stub

	}
}
