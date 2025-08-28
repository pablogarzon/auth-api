package com.example.authapi.services;

import java.util.List;
import java.util.UUID;

import com.example.authapi.dtos.UpdateUserDTO;
import com.example.authapi.dtos.UserResponseDTO;

public interface UserService {
	
	List<UserResponseDTO> getAllUsers();

	UserResponseDTO getUserById(UUID id);

	UserResponseDTO updateUser(UUID id, UpdateUserDTO updateUserDTO);

	void deleteUser(UUID id);
}
