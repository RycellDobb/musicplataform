package com.dagarcvj.music.plataform.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;
import com.dagarcvj.music.plataform.repositories.ArtistaRepository;
import com.dagarcvj.music.plataform.repositories.CancionRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: CancionController.java
 * @author: (c)2024 Angel
 * @created: 1 mar 2024, 1:02:24
 *
 */

@Service
public class CancionServiceImp implements CancionService {

	@Autowired
    private CancionRepository cancionRepository;
	@Autowired
	private ArtistaRepository artistaRepository;

    /**
     * Lista todas las canciones registradas en el sistema.
     *
     * @return Lista de canciones.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cancion> listarCanciones() {
        return (List<Cancion>) cancionRepository.findAll();
    }

    /**
     * Busca una canción por su identificador único.
     *
     * @param idCancion Identificador único de la canción a buscar.
     * @return Canción encontrada.
     * @throws EntityNotFoundException Si la canción no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Cancion buscarPorIdCancion(Long idCancion) {
        Optional<Cancion> cancion = cancionRepository.findById(idCancion);
        if (cancion.isEmpty()) {
            throw new EntityNotFoundException("No se encontró ninguna canción con el ID: " + idCancion);
        }
        return cancion.get();
    }

    /**
     * Registra una nueva canción en el sistema.
     *
     * @param cancion Canción a registrar.
     * @return Canción registrada.
     */
    @Override
    @Transactional
    public Cancion grabarCancion(Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    /**
     * Actualiza la información de una canción existente.
     *
     * @param idCancion Identificador único de la canción a actualizar.
     * @param cancion   Canción con los nuevos datos.
     * @return Canción actualizada.
     * @throws EntityNotFoundException Si la canción no se encuentra.
     */
    @Override
    @Transactional
    public Cancion actualizarCancion(Long idCancion, Cancion cancion) throws EntityNotFoundException {
        Optional<Cancion> oCancion = cancionRepository.findById(idCancion);
        if (oCancion.isEmpty()) {
            throw new EntityNotFoundException("La canción no existe");
        }
        cancion.setIdCancion(idCancion);
        return cancionRepository.save(cancion);
    }

    /**
     * Asigna un artista a una canción.
     *
     * @param idCancion Identificador único de la canción.
     * @param idArtista Identificador único del artista a asignar.
     * @return Canción con el artista asignado.
     * @throws EntityNotFoundException     Si la canción o el artista no se encuentra.
     * @throws IllegalOperationException    Si la canción ya tiene asignado un artista.
     */
    @Override
    @Transactional
    public Cancion asignarArtista(Long idCancion, Long idArtista)
            throws EntityNotFoundException, IllegalOperationException {
        Cancion cancion = cancionRepository.findById(idCancion)
                .orElseThrow(() -> new EntityNotFoundException("La canción no existe"));

        Artista artista = artistaRepository.findById(idArtista)
                .orElseThrow(() -> new EntityNotFoundException("El artista no existe"));

        if (cancion.getArtista() != null) {
            throw new IllegalOperationException("Esta canción ya tiene asignado un Artista. Artista: "
                    + cancion.getArtista().getNombre());
        }
        cancion.setArtista(artista);
        return cancionRepository.save(cancion);
    }

    /**
     * Elimina una canción por su identificador único.
     *
     * @param idCancion Identificador único de la canción a eliminar.
     * @throws EntityNotFoundException     Si la canción no se encuentra.
     * @throws IllegalOperationException    Si la canción tiene un artista asignado o pertenece a una lista de reproducción.
     */
    @Override
    @Transactional
    public void eliminarCancion(Long idCancion) throws EntityNotFoundException, IllegalOperationException {
        Cancion cancion = cancionRepository.findById(idCancion).orElseThrow(
                () -> new EntityNotFoundException("La canción no existe")
        );

        if (cancion.getArtista() != null) {
            throw new IllegalOperationException("No se puede eliminar esta canción ya que tiene un artista asignado");
        }
        if (cancion.getListasReproduccion() != null) {
            throw new IllegalOperationException("No se puede eliminar esta canción ya que se encuentra dentro de una lista de reproducción");
        }
        cancionRepository.deleteById(idCancion);
    }
}
