package com.dagarcvj.music.plataform.exception;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Excepción personalizada que indica que una entidad no ha sido encontrada en el sistema.
 */
public class EntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor que recibe un mensaje de error personalizado.
     * @param message El mensaje de error.
     */
	public EntityNotFoundException(String message) {
		super(message);
	}
}
