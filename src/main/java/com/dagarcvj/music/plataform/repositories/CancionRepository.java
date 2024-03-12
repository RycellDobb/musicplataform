package com.dagarcvj.music.plataform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dagarcvj.music.plataform.domain.Cancion;
/**
 * 
 * @file: CancionController.java
 * @author: (c)2024 Angel
 * @created: 1 mar 2024, 1:02:24
 *
 */

/**
 * Interfaz de repositorio para la entidad Cancion.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos relacionadas con la entidad Cancion.
 */

public interface CancionRepository extends JpaRepository<Cancion, Long> {

}
