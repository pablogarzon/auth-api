package com.example.authapi.exceptions;

public class UserNotFoundException  extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Usuario no encontrado.";

	public UserNotFoundException() {
        super(MESSAGE);
    }

}
