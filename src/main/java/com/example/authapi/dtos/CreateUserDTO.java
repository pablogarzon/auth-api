package com.example.authapi.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
	
    private String name;
    
    @Email(message = "formato de email incorrecto.")
    private String email;
    
    @Size(max = 12, min = 8, message = "la contrase�a debe tener un largo m�ximo de 12 y m�nimo 8.")
    @Pattern(regexp = "^((?=[^A-Z]*[A-Z][^A-Z]*$)(?=[^\\d]*\\d[^\\d]*\\d[^\\d]*$)[a-zA-Z\\d]*)$", message = "formato de contrase�a incorrecto.")
    private String password;
    
    private List<PhoneDTO> phones = new ArrayList<>();
}
