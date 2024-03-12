package com.dagarcvj.music.plataform.exception;


/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Excepción lanzada cuando se produce una operación ilegal en el sistema.
 */

public class IllegalOperationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
     * Crea una nueva instancia de IllegalOperationException con el mensaje de error especificado.
     * @param message El mensaje de error de la excepción.
     */
	
	public IllegalOperationException(String message) {
		super(message);
	}
}
