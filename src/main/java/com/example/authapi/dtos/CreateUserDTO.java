package com.example.authapi.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
	
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
}
