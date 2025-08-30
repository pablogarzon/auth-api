package com.example.authapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.PhoneDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.exceptions.EmailAlreadyUsedException;
import com.example.authapi.exceptions.InvalidCredentialsException;
import com.example.authapi.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@Autowired
	private ObjectMapper objectMapper;

	private LoginDTO loginDTO;

	private CreateUserDTO createUserDTO;

	@BeforeEach
	void setUp() {
		loginDTO = new LoginDTO();
		loginDTO.setEmail("test@example.com");
		loginDTO.setPassword("password123");

		createUserDTO = new CreateUserDTO();
		createUserDTO.setEmail("test@example.com");
		createUserDTO.setPassword("a2asfGfdfdf4");
		createUserDTO.setName("Test User");
	}

	@Test
	void signUp_shouldReturn201() throws JsonProcessingException, Exception {
		var phoneDTO = new PhoneDTO();
		phoneDTO.setCitycode(11);
		phoneDTO.setContrycode("+54");
		phoneDTO.setNumber(132L);
		var phones = List.of(phoneDTO);

		createUserDTO.setPhones(phones);

		var response = new UserResponseDTO();
		response.setEmail("email@mail.com");
		response.setPhones(phones);
		when(authService.signUp(any(CreateUserDTO.class))).thenReturn(response);
		mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createUserDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.phones[0].number").value(phoneDTO.getNumber()))
				.andExpect(jsonPath("$.phones[0].citycode").value(phoneDTO.getCitycode()))
				.andExpect(jsonPath("$.phones[0].contrycode").value(phoneDTO.getContrycode()));
	}

	@Test
	void signUp_shouldReturn400_wivenWeakPassword() throws JsonProcessingException, Exception {
		createUserDTO.setPassword("a2as");

		var response = new UserResponseDTO();

		when(authService.signUp(any(CreateUserDTO.class))).thenReturn(response);
		mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createUserDTO))).andExpect(status().isBadRequest());
	}

	@Test
	void signUp_shouldReturn409_wivenAlreadyUsedEmail() throws JsonProcessingException, Exception {
		when(authService.signUp(any(CreateUserDTO.class))).thenThrow(new EmailAlreadyUsedException());
		mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createUserDTO))).andExpect(status().isConflict());
	}

	@Test
	void login_shouldReturn200() throws JsonProcessingException, Exception {
		var response = new UserResponseDTO();
		response.setEmail("email@mail.com");
		response.setName("name");

		when(authService.login(any(LoginDTO.class))).thenReturn(response);
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(response.getEmail()))
				.andExpect(jsonPath("$.name").value(response.getName()));
	}

	@Test
	void login_shouldReturn403_givenInvalidUser() throws JsonProcessingException, Exception {
		var response = new UserResponseDTO();
		response.setEmail("email@mail.com");
		response.setName("name");

		when(authService.login(any(LoginDTO.class))).thenThrow(new InvalidCredentialsException());
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isUnauthorized());
	}
}
