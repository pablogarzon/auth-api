package com.example.authapi.exceptions;

public class InvalidCredentialsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Usuario o contraseña no válidos.";

	public InvalidCredentialsException() {
        super(MESSAGE);
    }

}
