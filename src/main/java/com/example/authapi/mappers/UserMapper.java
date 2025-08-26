package com.example.authapi.mappers;

import java.util.stream.Collectors;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.models.User;

public class UserMapper {

	private UserMapper() {
	}

	public static UserResponseDTO toDto(User user) {
		return new UserResponseDTO(user.getId(), user.getCreated(), user.getLastLogin(), null, user.isActive(),
				user.getName(), user.getEmail(), user.getPassword(),
				user.getPhones().stream().map(PhoneMapper::toDTO).collect(Collectors.toList()));

	}

	public static User toEntity(CreateUserDTO createUserDTO) {
		var user = new User(createUserDTO.getName(), createUserDTO.getEmail());
		user.setPhones(createUserDTO.getPhones());
		return user;
	}
}
