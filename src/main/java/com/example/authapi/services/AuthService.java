package com.example.authapi.services;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.UserResponseDTO;

public interface AuthService {
	
	UserResponseDTO signUp(CreateUserDTO createUserDTO) throws Exception;

	UserResponseDTO login(LoginDTO loginDTO);
	
	void logout(String token);
}
