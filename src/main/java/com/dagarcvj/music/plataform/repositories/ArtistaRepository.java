package com.dagarcvj.music.plataform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dagarcvj.music.plataform.domain.Artista;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

/**
 * Interfaz de repositorio para la entidad Artista.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos relacionadas con la entidad Artista.
 */

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
	/**
     * Busca artistas por su nombre.
     * @param nombre El nombre del artista a buscar.
     * @return Una lista de artistas que coinciden con el nombre especificado.
     */
	List<Artista> findByNombre(String nombre);
}
