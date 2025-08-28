package com.example.authapi.exceptions;

public class EmailAlreadyUsedException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Ya existe un usuario con ese email.";

	public EmailAlreadyUsedException() {
        super(MESSAGE);
    }
}
