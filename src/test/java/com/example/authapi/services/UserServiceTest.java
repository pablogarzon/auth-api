package com.example.authapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.authapi.dtos.PhoneDTO;
import com.example.authapi.dtos.UpdateUserDTO;
import com.example.authapi.exceptions.UserNotFoundException;
import com.example.authapi.models.Phone;
import com.example.authapi.models.User;
import com.example.authapi.repositories.UserRepository;
import com.example.authapi.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	UserServiceImpl userService;

	static UUID userId;

	@BeforeAll()
	static void init() {
		userId = UUID.randomUUID();
	}

	@Test
	void getAllUsers_shouldReturnUsers() {
		var user = new User("name", "email");
		user.setPhones(List.of(new Phone(1234567L, 351, "+54")));
		var list = List.of(user);
		when(userRepository.findAll()).thenReturn(list);
		var res = userService.getAllUsers();
		assertEquals(true, res.size() == 1);
		assertEquals(res.get(0).getEmail(), list.get(0).getEmail());
		assertEquals(true, res.get(0).getPhones().size() == 1);
		assertEquals(user.getPhones().get(0).getNumber(), res.get(0).getPhones().get(0).getNumber());
	}

	@Test
	void getUserById_shouldReturnUser() {
		var user = new User("name", "email");
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
		var res = userService.getUserById(userId);
		assertNotNull(res);
		assertEquals(res.getEmail(), user.getEmail());
	}

	@Test
	void getUserById_shouldThrowUserNotFoundException_whenInvalidUser() {
		when(userRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
	}
	
	@Test
	void updateUser_shouldReturnUpdatedUser() {
		boolean isActive = true;
		String name = "test";
		String email = "test@example.com";
		var spyPhone = spy(new Phone(1L, 351, "+54"));
		
		var user = new User(name, email);
		user.setPhones(List.of(spyPhone));
		User spyUser = spy(user);
		
		var userDTO = new UpdateUserDTO(isActive, name, email, "pass", Collections.emptyList());
		when(userRepository.findById(any())).thenReturn(Optional.of(spyUser));
		when(userRepository.save(any())).thenReturn(user);
		var res = userService.updateUser(userId, userDTO);
		assertNotNull(res);
		assertEquals(user.getEmail(), res.getEmail());
		verify(spyUser).setEmail(any());
		verify(spyUser).setActive(isActive);
		verify(spyUser).setPhones(any());
		verify(spyUser).setPhones(any());
		verify(spyPhone).setUser(user);
	}

	@Test
	void updateUser_shouldThrowUserNotFoundException_whenInvalidUser() {
		when(userRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, new UpdateUserDTO()));
	}
	
	

	@Test
	void deleteUser_shouldCallDeleteById() {
		userService.deleteUser(userId);
		verify(userRepository).deleteById(userId);
	}

}
