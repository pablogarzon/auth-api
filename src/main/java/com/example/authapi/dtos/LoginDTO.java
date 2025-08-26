package com.example.authapi.dtos;

import lombok.Data;

@Data
public class LoginDTO {
	private final String email;
	private final String password;
}
