package com.example.authapi.services.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authapi.dtos.CreateUserDTO;
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
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(final UserRepository userRepository, final JWTService jwtService,
			final AuthenticationManager authenticationManager, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponseDTO signUp(CreateUserDTO createUserDTO) throws Exception {
		if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent()) {
	        throw new Exception("Ya existe un usuario con ese email.");
	    }
		var user = UserMapper.toEntity(createUserDTO);
		user.setActive(true);
		user.setLastLogin(LocalDateTime.now());
		user.setCreated(LocalDateTime.now());
		user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
		var savedusr = userRepository.save(user);

		var response = UserMapper.toDto(savedusr);
		response.setToken(jwtService.generateToken(savedusr));

		return response;
	}

	@Override
	public UserResponseDTO login(LoginDTO loginDTO) {
		var authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
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
