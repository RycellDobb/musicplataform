package com.dagarcvj.music.plataform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dagarcvj.music.plataform.domain.ListaReproduccion;

/**
 * 
 * @file: ListaReproduccionController.java
 * @author: (c)2024 Andy
 * @created: 1 mar 2024, 1:05:10
 *
 */

/**
 * Interfaz de repositorio para la entidad ListaReproduccion.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos relacionadas con la entidad ListaReproduccion.
 */

public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, Long> {
	/**
     * Busca una lista de reproducción por su nombre.
     * @param nombre El nombre de la lista de reproducción a buscar.
     * @return Una lista de reproducción con el nombre especificado.
     */
	List<ListaReproduccion> findByNombre(String nombre);
}
