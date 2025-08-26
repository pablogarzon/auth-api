package com.example.authapi.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private UUID id;
	private LocalDateTime created;
	private LocalDateTime lastLogin;
	private String token;
	private boolean isActive;
	private String name;
	private String email;
	private String password;
	private List<PhoneDTO> phones;
}
