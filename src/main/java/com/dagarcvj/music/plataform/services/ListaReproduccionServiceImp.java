package com.dagarcvj.music.plataform.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.domain.ListaReproduccion;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;
import com.dagarcvj.music.plataform.repositories.CancionRepository;
import com.dagarcvj.music.plataform.repositories.ListaReproduccionRepository;


import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: ListaReproduccionController.java
 * @author: (c)2024 Andy
 * @created: 1 mar 2024, 1:05:10
 *
 */

@Service
public class ListaReproduccionServiceImp implements ListaReproduccionService {

    @Autowired
    private ListaReproduccionRepository listaReproduccionRepository;
    @Autowired
    private CancionRepository cancionRepository;

    /**
     * Lista todas las listas de reproducción registradas en el sistema.
     *
     * @return Lista de listas de reproducción.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListaReproduccion> listarListasReproduccion() {
        return (List<ListaReproduccion>) listaReproduccionRepository.findAll();
    }

    /**
     * Busca una lista de reproducción por su identificador único.
     *
     * @param idListaReproduccion Identificador único de la lista de reproducción a buscar.
     * @return Lista de reproducción encontrada.
     * @throws EntityNotFoundException Si la lista de reproducción no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public ListaReproduccion buscarPorIdListaReproduccion(Long idListaReproduccion) {
        Optional<ListaReproduccion> listaReproduccion = listaReproduccionRepository.findById(idListaReproduccion);
        if (listaReproduccion.isEmpty())
            throw new EntityNotFoundException("No se encontró ninguna lista de reproducción con el ID: " + idListaReproduccion);
        return listaReproduccion.get();
    }

    /**
     * Registra una nueva lista de reproducción en el sistema.
     *
     * @param listaReproduccion Lista de reproducción a registrar.
     * @return Lista de reproducción registrada.
     * @throws IllegalOperationException Si el nombre de la lista de reproducción ya existe.
     */
    @Override
    @Transactional
    public ListaReproduccion grabarListaReproduccion(ListaReproduccion listaReproduccion) {
        if (!listaReproduccionRepository.findByNombre(listaReproduccion.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre de la lista ya existe");
        }
        return listaReproduccionRepository.save(listaReproduccion);
    }

    /**
     * Actualiza la información de una lista de reproducción existente.
     *
     * @param idListaReproduccion Identificador único de la lista de reproducción a actualizar.
     * @param listaReproduccion   Lista de reproducción con los nuevos datos.
     * @return Lista de reproducción actualizada.
     * @throws EntityNotFoundException   Si la lista de reproducción no se encuentra.
     * @throws IllegalOperationException  Si el nombre de la lista de reproducción ya existe.
     */
    @Override
    @Transactional
    public ListaReproduccion actualizarListaReproduccion(Long idListaReproduccion, ListaReproduccion listaReproduccion) throws EntityNotFoundException {
        Optional<ListaReproduccion> oListaReproduccion = listaReproduccionRepository.findById(idListaReproduccion);
        if (oListaReproduccion.isEmpty()) {
            throw new EntityNotFoundException("La lista de reproducción no existe");
        }
        if (!listaReproduccionRepository.findByNombre(listaReproduccion.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre de la lista de reproducción ya existe");
        }
        listaReproduccion.setIdLista(idListaReproduccion);
        return listaReproduccionRepository.save(listaReproduccion);
    }

    /**
     * Elimina una lista de reproducción por su identificador único.
     *
     * @param idListaReproduccion Identificador único de la lista de reproducción a eliminar.
     * @throws EntityNotFoundException     Si la lista de reproducción no se encuentra.
     * @throws IllegalOperationException    Si la lista de reproducción tiene canciones asignadas.
     */
    @Override
    @Transactional
    public void eliminarListaReproduccion(Long idListaReproduccion) throws EntityNotFoundException, IllegalOperationException {
        ListaReproduccion listaReproduccion = listaReproduccionRepository.findById(idListaReproduccion).orElseThrow(
                () -> new EntityNotFoundException("La lista de reproducción no existe")
        );

        if (!listaReproduccion.getCancionesLista().isEmpty()) {
            throw new IllegalOperationException("La lista de reproducción tiene canciones asignadas");
        }

        listaReproduccionRepository.deleteById(idListaReproduccion);
    }

    /**
     * Asigna una canción a una lista de reproducción.
     *
     * @param idListaReproduccion Identificador único de la lista de reproducción.
     * @param idCancion          Identificador único de la canción a asignar.
     * @return Lista de reproducción con la canción asignada.
     * @throws EntityNotFoundException     Si la lista de reproducción o la canción no se encuentra.
     * @throws IllegalOperationException    Si la canción ya se encuentra en la lista de reproducción.
     */
    @Override
    @Transactional
    public ListaReproduccion asignarCancion(Long idListaReproduccion, Long idCancion) throws EntityNotFoundException, IllegalOperationException {
        ListaReproduccion listaReproduccion = listaReproduccionRepository.findById(idListaReproduccion).orElseThrow(
                () -> new EntityNotFoundException("La lista de reproducción no existe")
        );

        Cancion cancion = cancionRepository.findById(idCancion).orElseThrow(
                () -> new EntityNotFoundException("La canción con id " + idCancion + "no existe")
        );
        List<Cancion> cancionesActuales = listaReproduccion.getCancionesLista();

        if (cancionesActuales.contains(cancion)) {
            throw new IllegalOperationException("La canción ya se encuentra en la lista de reproducción");
        }
        cancionesActuales.add(cancion);
        listaReproduccion.setCancionesLista(cancionesActuales);
        return listaReproduccionRepository.save(listaReproduccion);
    }

    /**
     * Remueve una canción de una lista de reproducción.
     *
     * @param idListaReproduccion Identificador único de la lista de reproducción.
     * @param idCancion          Identificador único de la canción a remover.
     * @return Lista de reproducción sin la canción removida.
     * @throws EntityNotFoundException     Si la lista de reproducción o la canción no se encuentra.
     * @throws IllegalOperationException    Si la canción no se encuentra en la lista de reproducción.
     */
    @Override
    @Transactional
    public ListaReproduccion removerCancionDeLista(Long idListaReproduccion, Long idCancion) throws EntityNotFoundException, IllegalOperationException {
        ListaReproduccion listaReproduccion = listaReproduccionRepository.findById(idListaReproduccion)
                .orElseThrow(() -> new EntityNotFoundException("La lista de reproducción no existe"));

        Cancion cancion = cancionRepository.findById(idCancion)
                .orElseThrow(() -> new EntityNotFoundException("La canción con id " + idCancion + " no existe"));

        List<Cancion> cancionesActuales = listaReproduccion.getCancionesLista();

        if (!cancionesActuales.contains(cancion)) {
            throw new IllegalOperationException("La canción no se encuentra en la lista de reproducción");
        }

        cancionesActuales.remove(cancion);
        listaReproduccion.setCancionesLista(cancionesActuales);
        return listaReproduccionRepository.save(listaReproduccion);
    }

    /**
     * Muestra el artista de una canción específica dentro de una lista de reproducción.
     *
     * @param idCancion           Identificador único de la canción.
     * @param idListaReproduccion Identificador único de la lista de reproducción.
     * @return Artista de la canción en la lista de reproducción.
     * @throws EntityNotFoundException Si la lista de reproducción o la canción no se encuentra.
     */
    @Override
    public Artista mostrarArtistaPorIdCancionPorIdListaReproduccion(Long idListaReproduccion, Long idCancion ) {
        Optional<ListaReproduccion> listaReproduccionOptional = listaReproduccionRepository.findById(idListaReproduccion);

        if (listaReproduccionOptional.isPresent()) {
            ListaReproduccion listaReproduccion = listaReproduccionOptional.get();
            List<Cancion> canciones = listaReproduccion.getCancionesLista();

            for (Cancion cancion : canciones) {
                if (cancion.getIdCancion().equals(idCancion)) {
                    return cancion.getArtista();
                }
            }

            throw new EntityNotFoundException("La canción con ID " + idCancion + " no pertenece a la lista de reproducción con ID " + idListaReproduccion);
        } else {
            throw new EntityNotFoundException("Lista de reproducción no encontrada con ID: " + idListaReproduccion);
        }
    }
	
}