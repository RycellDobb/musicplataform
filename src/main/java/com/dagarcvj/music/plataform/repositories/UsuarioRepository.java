package com.dagarcvj.music.plataform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dagarcvj.music.plataform.domain.Usuario;

/**
 * 
 * @file: UsuarioController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 1:10:12
 *
 */

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
     * Busca usuarios por su nombre.
     * @param nombre El nombre del usuario a buscar.
     * @return Una lista de usuarios con el nombre especificado.
     */
	List<Usuario> findByNombre(String nombre);
	
	/**
     * Busca usuarios por su dirección de correo electrónico.
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Una lista de usuarios con la dirección de correo electrónico especificada.
     */
	List<Usuario> findByEmail(String email);
	
	/**
     * Busca usuarios por su número de DNI.
     * @param dni El número de DNI del usuario a buscar.
     * @return Una lista de usuarios con el número de DNI especificado.
     */
	List<Usuario> findByDni (String dni);
	
	/**
     * Busca usuarios que estén suscritos.
     * @return Una lista de usuarios que estén suscritos.
     */
	List<Usuario> findBySuscritoTrue();
}
