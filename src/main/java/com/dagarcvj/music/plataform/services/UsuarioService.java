package com.dagarcvj.music.plataform.services;

import java.util.List;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Usuario;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: UsuarioController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 1:10:12
 *
 */

public interface UsuarioService {
	/**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios.
     */
    List<Usuario> listarUsuarios();

    /**
     * Busca un usuario por su identificador único.
     *
     * @param idUsuario Identificador único del usuario a buscar.
     * @return Usuario encontrado.
     * @throws EntityNotFoundException Si el usuario no se encuentra.
     */
    Usuario buscarPorIdUsuario(Long idUsuario) throws EntityNotFoundException;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario Usuario a registrar.
     * @return Usuario registrado.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Usuario grabarUsuario(Usuario usuario) throws IllegalOperationException;

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param idUsuario Identificador único del usuario a actualizar.
     * @param usuario   Usuario con los nuevos datos.
     * @return Usuario actualizado.
     * @throws EntityNotFoundException  Si el usuario no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Usuario actualizarUsuario(Long idUsuario, Usuario usuario)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un usuario por su identificador único.
     *
     * @param idUsuario Identificador único del usuario a eliminar.
     * @throws EntityNotFoundException  Si el usuario no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    void eliminarUsuario(Long idUsuario) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna un seguidor a un usuario.
     *
     * @param idUsuarioSeguido  Identificador único del usuario que es seguido.
     * @param idUsuarioSeguidor Identificador único del usuario seguidor.
     * @return Usuario con el seguidor asignado.
     * @throws EntityNotFoundException  Si alguno de los usuarios no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Usuario asignarSeguidor(Long idUsuarioSeguido, Long idUsuarioSeguidor)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna un plan de membresía a un usuario.
     *
     * @param idUsuario      Identificador único del usuario.
     * @param idPlanMembresia Identificador único del plan de membresía a asignar.
     * @return Usuario con el plan de membresía asignado.
     * @throws EntityNotFoundException  Si el usuario o el plan de membresía no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Usuario asignarPlanMembresia(Long idUsuario, Long idPlanMembresia)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna una lista de reproducción a un usuario.
     *
     * @param idUsuario            Identificador único del usuario.
     * @param idListaReproduccion Identificador único de la lista de reproducción a asignar.
     * @return Usuario con la lista de reproducción asignada.
     * @throws EntityNotFoundException  Si el usuario o la lista de reproducción no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Usuario asignarListaReproduccion(Long idUsuario, Long idListaReproduccion)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Lista todos los usuarios activos en el sistema.
     *
     * @return Lista de usuarios activos.
     * @throws EntityNotFoundException  Si no se encuentran usuarios activos.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    List<Usuario> listarUsuariosActivos() throws EntityNotFoundException, IllegalOperationException;

    /**
     * Cancela la suscripción de un usuario.
     *
     * @param idUsuario Identificador único del usuario.
     * @return Usuario con la suscripción cancelada.
     * @throws EntityNotFoundException  Si el usuario no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Usuario cancelarSuscripcion(Long idUsuario) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Muestra el artista asociado a una canción, lista de reproducción y usuario específicos.
     *
     * @param idCancion           Identificador único de la canción.
     * @param idListaReproduccion Identificador único de la lista de reproducción.
     * @param idUsuario           Identificador único del usuario.
     * @return Artista asociado a la combinación especificada.
     * @throws EntityNotFoundException  Si la canción, lista de reproducción o usuario no se encuentra.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Artista mostrarArtistaporIdCancionporIdListaReproduccionporIdUsuario(Long idCancion, Long idListaReproduccion, Long idUsuario)
            throws EntityNotFoundException, IllegalOperationException;
}

