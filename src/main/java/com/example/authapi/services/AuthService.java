package com.example.authapi.services;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.exceptions.EmailAlreadyUsedException;
import com.example.authapi.exceptions.InvalidCredentialsException;

public interface AuthService {
	
	UserResponseDTO signUp(CreateUserDTO createUserDTO) throws EmailAlreadyUsedException;

	UserResponseDTO login(LoginDTO loginDTO) throws InvalidCredentialsException;
	
	void logout(String token);
}
