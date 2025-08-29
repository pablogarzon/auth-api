package com.example.authapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.exceptions.EmailAlreadyUsedException;
import com.example.authapi.services.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    
	@PostMapping("/sign-up")
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws EmailAlreadyUsedException {
		var createdUser = authService.signUp(createUserDTO);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO) {
    	UserResponseDTO authResponse = authService.login(loginDTO);
        return ResponseEntity.ok(authResponse);
    }
}
