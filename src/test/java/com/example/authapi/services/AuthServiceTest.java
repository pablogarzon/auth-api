package com.example.authapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.authapi.dtos.CreateUserDTO;
import com.example.authapi.dtos.LoginDTO;
import com.example.authapi.dtos.PhoneDTO;
import com.example.authapi.exceptions.EmailAlreadyUsedException;
import com.example.authapi.exceptions.InvalidCredentialsException;
import com.example.authapi.models.User;
import com.example.authapi.repositories.UserRepository;
import com.example.authapi.services.impl.AuthServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	JWTService jwtService;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	AuthServiceImpl authService;

	final private String name = "test";
	final private String email = "test@example.com";
	final private String password = "secure123";
	
	User expectedUser;
	
	@BeforeEach
	void init() {
		expectedUser = new User(name, email);
	}

	@Test
	void signUp_shouldCreateUserSuccessfully() throws EmailAlreadyUsedException {
		var phoneDTO = new PhoneDTO();
		phoneDTO.setCitycode(11);
		phoneDTO.setContrycode("+54");
		phoneDTO.setNumber(132L);
		var createUserDTO = new CreateUserDTO(name, email, password, List.of(phoneDTO));
		final String token = "token";
		when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.empty());

		when(userRepository.save(any(User.class))).thenReturn(expectedUser);
		when(jwtService.generateToken(expectedUser)).thenReturn(token);
		var response = authService.signUp(createUserDTO);
		assertEquals(email, response.getEmail());
		assertEquals(name, response.getName());
		assertEquals(token, response.getToken());
	}

	@Test
	public void signUp_shouldThrowEmailAlreadyUsedException_whenEmailAlreadyExists() throws EmailAlreadyUsedException {
		var createUserDTO = new CreateUserDTO(name, email, password, Collections.emptyList());
		when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(expectedUser));
		assertThrows(EmailAlreadyUsedException.class, () -> authService.signUp(createUserDTO));
	}

	@Test
	public void login_shouldAuthenticateSuccessfully() {
		LoginDTO loginDTO = new LoginDTO(email, password);
		User spyUser = spy(expectedUser);

		Authentication auth = mock(Authentication.class);
		when(authenticationManager.authenticate(any())).thenReturn(auth);
		when(auth.getPrincipal()).thenReturn(spyUser);

		when(userRepository.save(any(User.class))).thenReturn(expectedUser);
		when(jwtService.generateToken(expectedUser)).thenReturn("token");

		var response = authService.login(loginDTO);

		assertEquals(true, response.getToken() != null);

		verify(authenticationManager).authenticate(any());
		verify(spyUser).setLastLogin(any());
	}

	@Test
	void login_shouldThrowInvalidCredentialsException_whenAuthenticationFails() {
		// Arrange
		LoginDTO loginDTO = new LoginDTO(email, password);
		when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("msg"));

		// Act & Assert
		assertThrows(InvalidCredentialsException.class, () -> authService.login(loginDTO));
	}
}
