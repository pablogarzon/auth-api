package com.example.authapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.authapi.dtos.ErrorDTO;
import com.example.authapi.exceptions.EmailAlreadyUsedException;
import com.example.authapi.exceptions.InvalidCredentialsException;
import com.example.authapi.exceptions.UserNotFoundException;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDTO> notFound(UserNotFoundException ex) {
		final var status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(new ErrorDTO(status.value(), ex.getMessage()), status);
	}

	@ExceptionHandler(EmailAlreadyUsedException.class)
	public ResponseEntity<ErrorDTO> emailError(EmailAlreadyUsedException ex) {
		final var status = HttpStatus.CONFLICT;
		return new ResponseEntity<>(new ErrorDTO(status.value(), ex.getMessage()), status);
	}
	
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorDTO> badUser(InvalidCredentialsException ex) {
		final var status = HttpStatus.UNAUTHORIZED;
		return new ResponseEntity<>(new ErrorDTO(status.value(), ex.getMessage()), status);
	}	
	
}
