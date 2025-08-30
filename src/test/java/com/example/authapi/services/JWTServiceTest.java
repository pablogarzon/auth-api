package com.example.authapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.authapi.models.User;

import io.jsonwebtoken.ExpiredJwtException;

class JWTServiceTest {
	
	JWTService service;
	final String base64Key = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
	
	@BeforeEach
	void init() {		
		service = new JWTService(base64Key, 36000);
	}
	
	@Test
	void generateToken_shouldReturnToken() {
		var user = new User("name", "email");
		String token = service.generateToken(user);
		assertNotNull(token);
	}
	
	@Test
	void validateToken_shouldReturnToken() {
		var user = new User("name", "email");
		String token = service.generateToken(user);
		boolean result = service.validateToken(token, user);
		assertEquals(true, result);
	}
	
	@Test
	void validateToken_shouldThrowExpiredJwtException_whenTokenIsExpired() {
		service = new JWTService(base64Key, 1);
		var user = new User("name", "email");
		String token = service.generateToken(user);
		assertThrows(ExpiredJwtException.class, () -> service.validateToken(token, user)); 
	}
	
	@Test
	void extractSubject_shouldReturnUserEmail() {
		var user = new User("name", "email");
		String token = service.generateToken(user);
		var email = service.getSubject(token);
		assertEquals(user.getEmail(), email);
	}
}
