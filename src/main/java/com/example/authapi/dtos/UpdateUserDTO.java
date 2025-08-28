package com.example.authapi.dtos;

import java.util.List;

import lombok.Data;

@Data
public class UpdateUserDTO {
	private boolean isActive;
	private String name;
	private String email;
    private String password;
    private List<PhoneDTO> phones;
}
