package com.dagarcvj.music.plataform.User;
/**
 * @file: UserRepository.java
 * @author: (c) Angel
 * @created: 9/3/2024 11:02
 */

/**
 * Interfaz que define un repositorio para la entidad User.
 * Proporciona métodos para realizar operaciones de acceso a datos relacionadas con los usuarios en la base de datos.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * Busca un usuario por su nombre de usuario en la base de datos.
     *
     * @param username El nombre de usuario del usuario que se desea buscar.
     * @return Un objeto Optional que puede contener al usuario encontrado, o vacío si no se encontró ningún usuario con el nombre de usuario especificado.
     */
	Optional<User> findByUsername(String username);
}
