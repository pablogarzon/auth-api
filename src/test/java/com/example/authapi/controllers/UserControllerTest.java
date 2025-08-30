package com.example.authapi.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.authapi.dtos.PhoneDTO;
import com.example.authapi.dtos.UpdateUserDTO;
import com.example.authapi.dtos.UserResponseDTO;
import com.example.authapi.exceptions.UserNotFoundException;
import com.example.authapi.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	static UUID userId;

	PhoneDTO phoneDTO;

	UserResponseDTO userResponse;

	@BeforeEach()
	void init() {
		userId = UUID.randomUUID();

		phoneDTO = new PhoneDTO();
		phoneDTO.setCitycode(11);
		phoneDTO.setContrycode("+54");
		phoneDTO.setNumber(132L);
		var phones = List.of(phoneDTO);

		userResponse = new UserResponseDTO();
		userResponse.setEmail("email@mail.com");
		userResponse.setPhones(phones);
	}

	@Test
	void getAllUsers_shouldReturn200() throws JsonProcessingException, Exception {

		when(userService.getAllUsers()).thenReturn(List.of(userResponse));

		mockMvc.perform(get("/users/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(equalTo(1)))
				.andExpect(jsonPath("$[0].phones[0].number").value(phoneDTO.getNumber()));
	}

	@Test
	void getUsersById_shouldReturn200() throws JsonProcessingException, Exception {
		when(userService.getUserById(any())).thenReturn(userResponse);

		mockMvc.perform(get("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.phones[0].number").value(phoneDTO.getNumber()));
	}

	@Test
	void getUsersById_shouldReturn404_whenUserDoesNotExists() throws JsonProcessingException, Exception {

		when(userService.getUserById(any())).thenThrow(new UserNotFoundException());

		mockMvc.perform(get("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateUser_shouldReturn200() throws JsonProcessingException, Exception {
		var updateUserDTO = new UpdateUserDTO(true, "name", "test@example.ar", "abcdeE22abc", Collections.emptyList());

		when(userService.updateUser(any(), any())).thenReturn(userResponse);

		mockMvc.perform(put("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateUserDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.phones[0].number").value(phoneDTO.getNumber()));
	}

	@Test
	void removeUser_shouldReturn204() throws JsonProcessingException, Exception {
		doNothing().when(userService).deleteUser(any());

		mockMvc.perform(delete("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
