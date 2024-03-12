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
import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: ArtistaController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 9:24:06
 *
 */

@Service
public class ArtistaServiceImp implements ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    /**
     * Lista todos los artistas registrados en el sistema.
     *
     * @return Lista de artistas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Artista> listarArtistas() {
        return (List<Artista>) artistaRepository.findAll();
    }

    /**
     * Busca un artista por su identificador único.
     *
     * @param idArtista Identificador único del artista a buscar.
     * @return Artista encontrado.
     * @throws EntityNotFoundException Si el artista no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Artista buscarPorIdArtista(Long idArtista) {
        Optional<Artista> artista = artistaRepository.findById(idArtista);
        if (artista.isEmpty()) {
            throw new EntityNotFoundException("No se encontró ningún artista con el ID: " + idArtista);
        }
        return artista.get();
    }

    /**
     * Registra un nuevo artista en el sistema.
     *
     * @param artista Artista a registrar.
     * @return Artista registrado.
     * @throws IllegalOperationException Si el nombre del artista ya existe.
     */
    @Override
    @Transactional
    public Artista grabarArtista(Artista artista) {
        if (!artistaRepository.findByNombre(artista.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del artista ya existe");
        }
        return artistaRepository.save(artista);
    }

    /**
     * Actualiza la información de un artista existente.
     *
     * @param idArtista Identificador único del artista a actualizar.
     * @param artista   Artista con los nuevos datos.
     * @return Artista actualizado.
     * @throws EntityNotFoundException  Si el artista no se encuentra.
     * @throws IllegalOperationException Si el nombre del artista ya existe.
     */
    @Override
    @Transactional
    public Artista actualizarArtista(Long idArtista, Artista artista) throws EntityNotFoundException {
        Optional<Artista> oArtista = artistaRepository.findById(idArtista);
        if (oArtista.isEmpty()) {
            throw new EntityNotFoundException("El artista no existe");
        }
        if (!artistaRepository.findByNombre(artista.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del artista ya existe");
        }
        artista.setIdArtista(idArtista);
        return artistaRepository.save(artista);
    }

    /**
     * Elimina un artista por su identificador único.
     *
     * @param idArtista Identificador único del artista a eliminar.
     * @throws EntityNotFoundException  Si el artista no se encuentra.
     * @throws IllegalOperationException Si el artista tiene canciones asignadas.
     */
    @Override
    @Transactional
    public void eliminarArtista(Long idArtista) throws EntityNotFoundException, IllegalOperationException {
        Artista artista = artistaRepository.findById(idArtista).orElseThrow(
                () -> new EntityNotFoundException("El artista no existe")
        );

        if (!artista.getCanciones().isEmpty()) {
            throw new IllegalOperationException("El artista tiene canciones asignadas");
        }

        artistaRepository.deleteById(idArtista);
    }

    /**
     * Lista todas las canciones asociadas a un artista por su identificador único.
     *
     * @param idArtista Identificador único del artista.
     * @return Lista de canciones del artista.
     * @throws EntityNotFoundException Si el artista no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cancion> listarCancionesPorIdArtista(Long idArtista) {
        Optional<Artista> artistaOptional = artistaRepository.findById(idArtista);

        if (artistaOptional.isPresent()) {
            Artista artista = artistaOptional.get();
            return artista.getCanciones();
        } else {
            throw new EntityNotFoundException("Artista con id: " + idArtista + " no encontrado");
        }
    }
}
