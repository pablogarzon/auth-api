package com.example.authapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.UpdateUserDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.services.UserService;

@RestController("/users")
public class UserController {
	
	private final UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
	
	@PostMapping("/")
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
		var createdUser = service.createUser(createUserDTO);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID id) {
		UserResponseDTO user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }
	
	@GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		List<UserResponseDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

	//UpdateUserDTO?
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
    	UserResponseDTO updatedUser = service.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    	service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}


			