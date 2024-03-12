package com.dagarcvj.music.plataform.services;

import java.util.List;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;
import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

public interface ArtistaService {
	
	
	/**
     * Lista todos los artistas.
     *
     * @return Lista de artistas.
     */
    List<Artista> listarArtistas();

    /**
     * Busca un artista por su ID.
     *
     * @param idArtista ID del artista a buscar.
     * @return Artista encontrado.
     * @throws EntityNotFoundException Si no se encuentra el artista.
     */
    Artista buscarPorIdArtista(Long idArtista) throws EntityNotFoundException;

    /**
     * Graba un nuevo artista.
     *
     * @param artista Artista a grabar.
     * @return Artista grabado.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Artista grabarArtista(Artista artista) throws IllegalOperationException;

    /**
     * Actualiza un artista existente.
     *
     * @param idArtista ID del artista a actualizar.
     * @param artista   Artista con los nuevos datos.
     * @return Artista actualizado.
     * @throws EntityNotFoundException  Si no se encuentra el artista.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    Artista actualizarArtista(Long idArtista, Artista artista)
            throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un artista por su ID.
     *
     * @param idArtista ID del artista a eliminar.
     * @throws EntityNotFoundException  Si no se encuentra el artista.
     * @throws IllegalOperationException Si la operación es ilegal.
     */
    void eliminarArtista(Long idArtista) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Lista todas las canciones asociadas a un artista por su ID.
     *
     * @param idArtista ID del artista.
     * @return Lista de canciones del artista.
     */
    List<Cancion> listarCancionesPorIdArtista(Long idArtista);
}