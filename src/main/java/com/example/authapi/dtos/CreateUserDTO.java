package com.example.authapi.dtos;

import java.util.List;

import com.example.authapi.models.Phone;

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
    private List<Phone> phones;
}
