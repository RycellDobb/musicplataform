package com.dagarcvj.music.plataform.services;

import java.util.List;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.ListaReproduccion;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: ListaReproduccionController.java
 * @author: (c)2024 Andy
 * @created: 1 mar 2024, 1:05:10
 *
 */


public interface ListaReproduccionService {
	/**
     * Lista todas las listas de reproducción.
     *
     * @return Lista de listas de reproducción.
     */
    List<ListaReproduccion> listarListasReproduccion();

    /**
     * Busca una lista de reproducción por su ID.
     *
     * @param idListaReproduccion ID de la lista de reproducción a buscar.
     * @return Lista de reproducción encontrada.
     * @throws EntityNotFoundException Si no se encuentra la lista de reproducción.
     */
    ListaReproduccion buscarPorIdListaReproduccion(Long idListaReproduccion) throws EntityNotFoundException;

    /**
     * Graba una nueva lista de reproducción.
     *
     * @param listaReproduccion Lista de reproducción a grabar.
     * @return Lista de reproducción grabada.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    ListaReproduccion grabarListaReproduccion(ListaReproduccion listaReproduccion) throws IllegalOperationException;

    /**
     * Actualiza una lista de reproducción existente.
     *
     * @param idListaReproduccion ID de la lista de reproducción a actualizar.
     * @param listaReproduccion   Lista de reproducción con los nuevos datos.
     * @return Lista de reproducción actualizada.
     * @throws EntityNotFoundException  Si no se encuentra la lista de reproducción.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    ListaReproduccion actualizarListaReproduccion(Long idListaReproduccion, ListaReproduccion listaReproduccion)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina una lista de reproducción por su ID.
     *
     * @param idListaReproduccion ID de la lista de reproducción a eliminar.
     * @throws EntityNotFoundException  Si no se encuentra la lista de reproducción.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    void eliminarListaReproduccion(Long idListaReproduccion)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna una canción a una lista de reproducción.
     *
     * @param idListaReproduccion ID de la lista de reproducción.
     * @param idCancion           ID de la canción a asignar.
     * @return Lista de reproducción con la canción asignada.
     * @throws EntityNotFoundException  Si no se encuentra la lista de reproducción o la canción.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    ListaReproduccion asignarCancion(Long idListaReproduccion, Long idCancion)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Remueve una canción de una lista de reproducción.
     *
     * @param idListaReproduccion ID de la lista de reproducción.
     * @param idCancion           ID de la canción a remover.
     * @return Lista de reproducción con la canción removida.
     * @throws EntityNotFoundException  Si no se encuentra la lista de reproducción o la canción.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    ListaReproduccion removerCancionDeLista(Long idListaReproduccion, Long idCancion)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Muestra el artista de una canción en una lista de reproducción.
     *
     * @param idCancion           ID de la canción.
     * @param idListaReproduccion ID de la lista de reproducción.
     * @return Artista de la canción en la lista de reproducción.
     */
    Artista mostrarArtistaPorIdCancionPorIdListaReproduccion(Long idCancion, Long idListaReproduccion);
}
