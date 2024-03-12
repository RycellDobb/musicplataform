package com.dagarcvj.music.plataform.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Cancion;
import com.dagarcvj.music.plataform.domain.ListaReproduccion;
import com.dagarcvj.music.plataform.domain.PlanMembresia;
import com.dagarcvj.music.plataform.domain.Usuario;
import com.dagarcvj.music.plataform.exception.IllegalOperationException;
import com.dagarcvj.music.plataform.repositories.ListaReproduccionRepository;
import com.dagarcvj.music.plataform.repositories.PlanMembresiaRepository;
import com.dagarcvj.music.plataform.repositories.UsuarioRepository;


import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @file: UsuarioController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 1:10:12
 *
 */

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PlanMembresiaRepository planMembresiaRepository;
    @Autowired
    private ListaReproduccionRepository listaReproduccionRepository;
    //@Autowired
    //private PlanMembresiaRepository planMembresiaRepository;

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param idUsuario Identificador único del usuario a buscar.
     * @return Usuario encontrado.
     * @throws EntityNotFoundException Si el usuario no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorIdUsuario(Long idUsuario) throws EntityNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) throw new EntityNotFoundException("No se encontró ningún usuario con el ID: " + idUsuario);
        return usuario.get();
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario Usuario a registrar.
     * @return Usuario registrado.
     * @throws IllegalOperationException Si el nombre, email o DNI del usuario ya existen.
     */
    @Override
    @Transactional
    public Usuario grabarUsuario(Usuario usuario) throws IllegalOperationException {
        if (!usuarioRepository.findByNombre(usuario.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del usuario ya existe");
        }
        if (!usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
            throw new IllegalOperationException("El email del usuario ya existe");
        }
        if (!usuarioRepository.findByDni(usuario.getDni()).isEmpty()) {
            throw new IllegalOperationException("El DNI del usuario ya existe");
        }
        usuario.setSuscrito(false);
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su identificador único.
     *
     * @param idUsuario Identificador único del usuario a eliminar.
     * @throws EntityNotFoundException     Si el usuario no se encuentra.
     * @throws IllegalOperationException    Si el usuario tiene seguidores, una lista de reproducción o un plan de membresía asignados.
     */
    @Override
    @Transactional
    public void eliminarUsuario(Long idUsuario) throws EntityNotFoundException, IllegalOperationException {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntityNotFoundException("El usuario no existe")
        );
        if (usuario.getUsuarioSeguidor() != null) {
            throw new IllegalOperationException("No se puede eliminar un usuario que está siendo seguido por otro usuario");
        }
        if (usuario.getListaReproduccion() != null) {
            throw new IllegalOperationException("No se puede eliminar este usuario ya que tiene una lista de reproducción");
        }
        if (usuario.getPlanMembresia() != null) {
            throw new IllegalOperationException("No se puede eliminar este usuario ya que tiene un Plan de Membresía");
        }
        usuarioRepository.deleteById(idUsuario);
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param idUsuario Identificador único del usuario a actualizar.
     * @param usuario   Usuario con los nuevos datos.
     * @return Usuario actualizado.
     * @throws EntityNotFoundException   Si el usuario no se encuentra.
     * @throws IllegalOperationException  Si el nombre, email o DNI del usuario ya existen.
     */
    @Override
    @Transactional
    public Usuario actualizarUsuario(Long idUsuario, Usuario usuario) throws EntityNotFoundException, IllegalOperationException {
        Optional<Usuario> oUsuario = usuarioRepository.findById(idUsuario);
        if (oUsuario.isEmpty()) {
            throw new EntityNotFoundException("El usuario no existe");
        }
        if (!usuarioRepository.findByNombre(usuario.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del usuario ya existe");
        }
        if (!usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
            throw new IllegalOperationException("El email del usuario ya existe");
        }
        if (!usuarioRepository.findByDni(usuario.getDni()).isEmpty()) {
            throw new IllegalOperationException("El DNI del usuario ya existe");
        }
        usuario.setIdUsuario(idUsuario);
        return usuarioRepository.save(usuario);
    }

    /**
     * Asigna un seguidor a un usuario.
     *
     * @param idUsuarioSeguido  Identificador único del usuario que será seguido.
     * @param idUsuarioSeguidor Identificador único del usuario seguidor.
     * @return Usuario seguido actualizado.
     * @throws EntityNotFoundException   Si alguno de los usuarios no se encuentra.
     * @throws IllegalOperationException  Si intenta seguirse a sí mismo o el usuario ya está siendo seguido por otro.
     */
    @Override
    @Transactional
    public Usuario asignarSeguidor(Long idUsuarioSeguido, Long idUsuarioSeguidor) throws EntityNotFoundException, IllegalOperationException {
        Usuario usuarioSeguido = usuarioRepository.findById(idUsuarioSeguido)
                .orElseThrow(() -> new EntityNotFoundException("El usuario seguido no existe"));

        Usuario usuarioSeguidor = usuarioRepository.findById(idUsuarioSeguidor)
                .orElseThrow(() -> new EntityNotFoundException("El usuario seguidor no existe"));
        if (idUsuarioSeguido.equals(idUsuarioSeguidor)) {
            throw new IllegalOperationException("No se puede seguir a uno mismo");
        }
        if (usuarioSeguido.getUsuarioSeguidor() != null) {
            throw new IllegalOperationException("Este usuario ya está siendo seguido por otro usuario");
        }
        usuarioSeguido.setUsuarioSeguidor(usuarioSeguidor);
        return usuarioRepository.save(usuarioSeguido);
    }

    /**
     * Asigna un plan de membresía a un usuario.
     *
     * @param idUsuario      Identificador único del usuario.
     * @param idPlanMembresia Identificador único del plan de membresía.
     * @return Usuario actualizado con el plan de membresía asignado.
     * @throws EntityNotFoundException   Si el usuario o el plan de membresía no se encuentran.
     * @throws IllegalOperationException  Si el usuario ya tiene un plan de membresía asignado.
     */
    @Override
    @Transactional
    public Usuario asignarPlanMembresia(Long idUsuario, Long idPlanMembresia) throws EntityNotFoundException, IllegalOperationException {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        PlanMembresia planMembresia = planMembresiaRepository.findById(idPlanMembresia)
                .orElseThrow(() -> new EntityNotFoundException("El plan de membresía no existe"));

        if (usuario.getPlanMembresia() != null) {
            throw new IllegalOperationException("Este usuario ya tiene asignado un plan de membresía. Plan actual: "
                    + usuario.getPlanMembresia().getNombre());
        }
        usuario.setPlanMembresia(planMembresia);
        usuario.setSuscrito(true);
        return usuarioRepository.save(usuario);
    }

    /**
     * Asigna una lista de reproducción a un usuario.
     *
     * @param idUsuario           Identificador único del usuario.
     * @param idListaReproduccion Identificador único de la lista de reproducción.
     * @return Usuario actualizado con la lista de reproducción asignada.
     * @throws EntityNotFoundException   Si el usuario o la lista de reproducción no se encuentran.
     * @throws IllegalOperationException  Si el usuario ya tiene una lista de reproducción asignada.
     */
    @Override
    @Transactional
    public Usuario asignarListaReproduccion(Long idUsuario, Long idListaReproduccion) throws EntityNotFoundException, IllegalOperationException {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new EntityNotFoundException("El usuario no existe")
        );

        ListaReproduccion listaReproduccion = listaReproduccionRepository.findById(idListaReproduccion).orElseThrow(
                () -> new EntityNotFoundException("La lista de reproducción no existe")
        );
        if (usuario.getPlanMembresia() != null) {
            throw new IllegalOperationException("Este usuario ya tiene asignado una Lista de Reproducción. Lista actual: "
                    + usuario.getPlanMembresia().getNombre());
        }

        usuario.setListaReproduccion(listaReproduccion);
        return usuarioRepository.save(usuario);
    }

    /**
     * Lista todos los usuarios activos (suscritos).
     *
     * @return Lista de usuarios activos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findBySuscritoTrue();
    }

    /**
     * Cancela la suscripción de un usuario, eliminando el plan de membresía asignado.
     *
     * @param idUsuario Identificador único del usuario.
     * @return Usuario actualizado sin plan de membresía y marcado como no suscrito.
     * @throws EntityNotFoundException   Si el usuario no se encuentra.
     * @throws IllegalOperationException  Si el usuario no tiene asignado ningún plan de membresía.
     */
    @Override
    @Transactional
    public Usuario cancelarSuscripcion(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        if (usuario.getPlanMembresia() == null) {
            throw new IllegalOperationException("Este usuario no tiene asignado ningún plan de membresía");
        }

        usuario.setPlanMembresia(null);
        usuario.setSuscrito(false);
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene el artista de una canción asociada a una lista de reproducción de un usuario.
     *
     * @param idCancion           Identificador único de la canción.
     * @param idListaReproduccion Identificador único de la lista de reproducción.
     * @param idUsuario           Identificador único del usuario.
     * @return Artista de la canción.
     * @throws EntityNotFoundException Si la canción, la lista de reproducción o el usuario no se encuentran.
     */
    @Override
    @Transactional(readOnly = true)
    public Artista mostrarArtistaporIdCancionporIdListaReproduccionporIdUsuario(Long idCancion,
                                                                                   Long idListaReproduccion,
                                                                                   Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        ListaReproduccion listaReproduccion = usuario.getListaReproduccion();
        if (listaReproduccion == null || !listaReproduccion.getIdLista().equals(idListaReproduccion)) {
            throw new EntityNotFoundException("La lista de reproducción no pertenece al usuario especificado");
        }

        Cancion cancion = listaReproduccion.getCancionesLista().stream()
                .filter(c -> c.getIdCancion().equals(idCancion))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("La canción no pertenece a la lista de reproducción"));

        return cancion.getArtista();
    }
}
