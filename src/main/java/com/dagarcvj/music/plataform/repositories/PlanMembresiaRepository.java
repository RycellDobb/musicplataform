package com.dagarcvj.music.plataform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dagarcvj.music.plataform.domain.PlanMembresia;

/**
 * 
 * @file: PlanMembresiaController.java
 * @author: (c)2024 Junior
 * @created: 1 mar 2024, 1:08:06
 *
 */

/**
 * Interfaz de repositorio para la entidad PlanMembresia.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos relacionadas con la entidad PlanMembresia.
 */

public interface PlanMembresiaRepository extends JpaRepository<PlanMembresia, Long> {
		/**
     * Busca un plan de membresía por su nombre.
     * @param nombre El nombre del plan de membresía a buscar.
     * @return Un plan de membresía con el nombre especificado.
     */
	List<PlanMembresia> findByNombre(String nombre);
}
