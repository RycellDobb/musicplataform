package com.dagarcvj.music.plataform.exception;

public class VersionMismatchException extends RuntimeException{
private static final long serialVersionUID = 1L;
	
	/**
     * Constructor que recibe un mensaje de error personalizado.
     * @param message El mensaje de error.
     */
	public VersionMismatchException (String message) {
		super(message);
	}
}
