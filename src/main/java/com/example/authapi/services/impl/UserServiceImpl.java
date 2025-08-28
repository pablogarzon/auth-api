package com.example.authapi.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authapi.dtos.UpdateUserDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.mappers.UserMapper;
import com.example.authapi.repositories.UserRepository;
import com.example.authapi.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		var users = userRepository.findAll();
		return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public UserResponseDTO getUserById(UUID id) {
		var user = userRepository.findById(id).orElseThrow();
		return UserMapper.toDto(user);
	}

	@Override
	public UserResponseDTO updateUser(UUID id, UpdateUserDTO updateUserDTO) {
		var user = userRepository.findById(id).orElseThrow();
		user.setActive(updateUserDTO.isActive());
		user.setEmail(updateUserDTO.getEmail());
		user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
		user.setPhones(updateUserDTO.getPhones());
		return UserMapper.toDto(userRepository.save(user));
	}

	@Override
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}
}
