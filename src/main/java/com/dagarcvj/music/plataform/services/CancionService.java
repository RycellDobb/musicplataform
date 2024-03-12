package com.dagarcvj.music.plataform.services;

import java.util.List;

import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: CancionController.java
 * @author: (c)2024 Angel
 * @created: 1 mar 2024, 1:02:24
 *
 */


public interface CancionService {
    /**
     * Lista todas las canciones.
     *
     * @return Lista de canciones.
     */
    List<Cancion> listarCanciones();

    /**
     * Busca una canción por su ID.
     *
     * @param idCancion ID de la canción a buscar.
     * @return Canción encontrada.
     * @throws EntityNotFoundException Si no se encuentra la canción.
     */
    Cancion buscarPorIdCancion(Long idCancion) throws EntityNotFoundException;

    /**
     * Graba una nueva canción.
     *
     * @param cancion Canción a grabar.
     * @return Canción grabada.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Cancion grabarCancion(Cancion cancion) throws IllegalOperationException;

    /**
     * Actualiza una canción existente.
     *
     * @param idCancion ID de la canción a actualizar.
     * @param cancion   Canción con los nuevos datos.
     * @return Canción actualizada.
     * @throws EntityNotFoundException  Si no se encuentra la canción.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Cancion actualizarCancion(Long idCancion, Cancion cancion)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina una canción por su ID.
     *
     * @param idCancion ID de la canción a eliminar.
     * @throws EntityNotFoundException  Si no se encuentra la canción.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    void eliminarCancion(Long idCancion) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna un artista a una canción.
     *
     * @param idCancion ID de la canción.
     * @param idArtista ID del artista a asignar.
     * @return Canción con el artista asignado.
     * @throws EntityNotFoundException  Si no se encuentra la canción o el artista.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Cancion asignarArtista(Long idCancion, Long idArtista)
            throws EntityNotFoundException, IllegalOperationException;

}
