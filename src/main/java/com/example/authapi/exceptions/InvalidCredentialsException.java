package com.example.authapi.exceptions;

public class InvalidCredentialsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Usuario o contrase�a no v�lidos.";

	public InvalidCredentialsException() {
        super(MESSAGE);
    }

}
