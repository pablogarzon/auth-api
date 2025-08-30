package com.example.authapi.mappers;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.models.User;

public class UserMapper {

	private UserMapper() {
	}

	public static UserResponseDTO toDto(User user) {
		return new UserResponseDTO(user.getId(), user.getCreated(), user.getLastLogin(), null, user.isActive(),
				user.getName(), user.getEmail(), user.getPassword(), PhoneMapper.toDTO(user.getPhones()));

	}

	public static User toEntity(CreateUserDTO createUserDTO) {
		return new User(createUserDTO.getName(), createUserDTO.getEmail(),
				PhoneMapper.toEntity(createUserDTO.getPhones()));
	}
}
