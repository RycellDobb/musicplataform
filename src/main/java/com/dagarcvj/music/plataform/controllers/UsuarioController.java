package com.dagarcvj.music.plataform.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.Usuario;
import com.dagarcvj.music.plataform.dto.ArtistaDTO;
import com.dagarcvj.music.plataform.dto.UsuarioDTO;
import com.dagarcvj.music.plataform.services.UsuarioService;
import com.dagarcvj.music.plataform.util.ApiResponse;

import jakarta.validation.Valid;
/**
 * 
 * @file: UsuarioController.java
 * @author: (c)2024 Cleysi
 * @created: 1 mar 2024, 1:10:12
 *
 */

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
 	private ModelMapper modelMapper;
    
    /**
     * Método que maneja una solicitud GET para listar todos los usuarios.
     * 
     * @return ResponseEntity que contiene una lista de usuarios en forma de UsuarioDTOs si se encuentran,
     *         o un ResponseEntity sin contenido si no se encuentran usuarios.
     */

    @GetMapping(value = "/listar", headers = "X-VERSION=1")
    public ResponseEntity<?> listarUsuarios() {
    	List<Usuario> usuarios = usuarioService.listarUsuarios();
 	    if (usuarios == null || usuarios.isEmpty()) {
 	        return ResponseEntity.noContent().build();
 	    } else {
 	        List<UsuarioDTO> usuarioDTOs = usuarios.stream()
 	                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
 	                .collect(Collectors.toList());
 	        
 	       for(UsuarioDTO usuarioMap : usuarioDTOs) {
	        	usuarioMap.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioMap.getIdUsuario())).withSelfRel());
	        	usuarioMap.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
	        }
 	        ApiResponse<List<UsuarioDTO>> response = new ApiResponse<>(true, "Lista de usuarios obtenida con éxito", usuarioDTOs);
 	        return ResponseEntity.ok(response);
 	    }
    }
    
    /**
     * Método que maneja una solicitud GET para listar todos los usuarios activos.
     * 
     * @return ResponseEntity que contiene una lista de usuarios activos en forma de UsuarioDTOs si se encuentran,
     *         o un ResponseEntity sin contenido si no se encuentran usuarios activos.
     */

    @GetMapping(value = "/listar/activos", headers = "X-VERSION=1")
    public ResponseEntity<?> listarUsuariosActivos(){
    	List<Usuario> usuarios = usuarioService.listarUsuariosActivos();
 	    if (usuarios == null || usuarios.isEmpty()) {
 	        return ResponseEntity.noContent().build();
 	    } else {
 	        List<UsuarioDTO> usuarioDTOs = usuarios.stream()
 	                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
 	                .collect(Collectors.toList());
 	        
 	       for(UsuarioDTO usuarioMap : usuarioDTOs) {
	        	usuarioMap.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioMap.getIdUsuario())).withSelfRel());
	        	usuarioMap.add(linkTo(methodOn(UsuarioController.class).listarUsuariosActivos()).withRel("usuario activo"));
	        	usuarioMap.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
	        }
 	        ApiResponse<List<UsuarioDTO>> response = new ApiResponse<>(true, "Lista de usuarios obtenida con éxito", usuarioDTOs);
 	        return ResponseEntity.ok(response);
 	    }

    }

    /**
     * Método que maneja una solicitud GET para obtener un usuario por su identificador.
     * 
     * @param id el identificador del usuario que se desea obtener.
     * @return ResponseEntity que contiene el usuario con el identificador especificado en forma de UsuarioDTO si se encuentra,
     *         o un ResponseEntity sin contenido si no se encuentra ningún usuario con el identificador especificado.
     */

    @GetMapping(value = "/listar/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
    	Usuario usuario = usuarioService.buscarPorIdUsuario(id);
        UsuarioDTO  usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTO.getIdUsuario())).withSelfRel());
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
    
        ApiResponse <UsuarioDTO> response =new ApiResponse<>(true, "Usuario obtenido con éxito", usuarioDTO);
    	return ResponseEntity.ok(response);
        
    }

    /**
     * Método que maneja una solicitud POST para crear un nuevo usuario.
     * 
     * @param usuarioDTO la representación DTO del usuario que se desea crear.
     * @return ResponseEntity que indica el éxito de la creación del usuario junto con la representación DTO del usuario creado si la operación es exitosa.
     */

    @PostMapping(value = "/crear", headers = "X-VERSION=1")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
    	Usuario  usuario = modelMapper.map(usuarioDTO, Usuario.class);
    	usuarioService.grabarUsuario(usuario);
    	UsuarioDTO usuarioDTOCreado=modelMapper.map(usuario, UsuarioDTO.class);
    	
    	usuarioDTOCreado.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTOCreado.getIdUsuario())).withSelfRel());
    	usuarioDTOCreado.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
    
        ApiResponse <UsuarioDTO> response =new ApiResponse<>(true, "Usuario guardado con éxito", usuarioDTOCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para editar un usuario existente.
     * 
     * @param usuarioDTO la representación DTO actualizada del usuario.
     * @param id el identificador del usuario que se desea editar.
     * @return ResponseEntity que indica el éxito de la actualización del usuario junto con la representación DTO del usuario actualizado si la operación es exitosa.
     */

    @PutMapping(value = "/editar/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
    	Usuario  usuario = modelMapper.map(usuarioDTO, Usuario.class);
    	usuarioService.actualizarUsuario(id, usuario);
    	UsuarioDTO usuarioDTOActualizado=modelMapper.map(usuario, UsuarioDTO.class);
    	
    	usuarioDTOActualizado.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTOActualizado.getIdUsuario())).withSelfRel());
    	usuarioDTOActualizado.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
        ApiResponse <UsuarioDTO> response =new ApiResponse<>(true, "Usuario actualiado con éxito", usuarioDTOActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para que un usuario siga a otro usuario.
     * 
     * @param idUsuarioSeguido el identificador del usuario que va a ser seguido.
     * @param idUsuarioSeguidor el identificador del usuario que sigue.
     * @return ResponseEntity que indica el éxito de la asignación del seguidor al usuario especificado junto con la representación DTO del usuario actualizado si la operación es exitosa.
     */

    @PutMapping(value = "/editar/{idUsuarioSeguido}/esseguidopor/{idUsuarioSeguidor}", headers = "X-VERSION=1")
    public ResponseEntity<?> seguirUsuario(@PathVariable Long idUsuarioSeguido, @PathVariable Long idUsuarioSeguidor) {
        Usuario usuario = usuarioService.asignarSeguidor(idUsuarioSeguido, idUsuarioSeguidor);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTO.getIdUsuario())).withSelfRel());
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).seguirUsuario(idUsuarioSeguido, idUsuarioSeguidor)).withRel("seguidor"));
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
        ApiResponse<UsuarioDTO> response = new ApiResponse<>(true, "Seguidor asignado con éxito", usuarioDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para cancelar la suscripción de un usuario.
     * 
     * @param id el identificador del usuario cuya suscripción se desea cancelar.
     * @return ResponseEntity que indica el éxito de la cancelación de la suscripción junto con la representación DTO del usuario actualizado si la operación es exitosa.
     */

    @PutMapping(value = "/editar/cancelar-suscripcion/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> cancelarSuscripcion(@PathVariable Long id){
    	Usuario usuario = usuarioService.cancelarSuscripcion(id);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTO.getIdUsuario())).withSelfRel());
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).cancelarSuscripcion(id)).withRel("cancelacion"));
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
        ApiResponse<UsuarioDTO> response = new ApiResponse<>(true, "Suscripción removida con éxito", usuarioDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para asignar un plan de membresía a un usuario.
     * 
     * @param idUsuario el identificador del usuario al que se desea asignar el plan de membresía.
     * @param idPlanMembresia el identificador del plan de membresía que se desea asignar al usuario.
     * @return ResponseEntity que indica el éxito de la asignación del plan de membresía al usuario especificado junto con la representación DTO del usuario actualizado si la operación es exitosa.
     */

    @PutMapping(value = "/editar/{idUsuario}/suscribir/{idPlanMembresia}", headers = "X-VERSION=1")
    public ResponseEntity<?> asignarPlanMembresia(@PathVariable Long idUsuario, @PathVariable Long idPlanMembresia) {
        Usuario usuario = usuarioService.asignarPlanMembresia(idUsuario, idPlanMembresia);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTO.getIdUsuario())).withSelfRel());
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).asignarPlanMembresia(idUsuario, idPlanMembresia)).withRel("asignacion"));
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
        ApiResponse<UsuarioDTO> response = new ApiResponse<>(true, "Plan de membresía asignado con éxito", usuarioDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Método que maneja una solicitud PUT para asignar una lista de reproducción a un usuario.
     * 
     * @param idUsuario el identificador del usuario al que se desea asignar la lista de reproducción.
     * @param idListaReproduccion el identificador de la lista de reproducción que se desea asignar al usuario.
     * @return ResponseEntity que indica el éxito de la asignación de la lista de reproducción al usuario especificado junto con la representación DTO del usuario actualizado si la operación es exitosa.
     */

    @PutMapping(value = "/editar/{idUsuario}/asignar-lista-reproduccion/{idListaReproduccion}", headers = "X-VERSION=1")
    public ResponseEntity<?> asignarListaReproduccion(@PathVariable Long idUsuario, @PathVariable Long idListaReproduccion) {
        Usuario usuario = usuarioService.asignarListaReproduccion(idUsuario, idListaReproduccion);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuarioDTO.getIdUsuario())).withSelfRel());
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).asignarListaReproduccion(idUsuario, idListaReproduccion)).withRel("asignacion"));
        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
    
        ApiResponse<UsuarioDTO> response = new ApiResponse<>(true, "Lista de reproducción asignada con éxito", usuarioDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Método que maneja una solicitud DELETE para eliminar un usuario por su identificador.
     * 
     * @param id el identificador del usuario que se desea eliminar.
     * @return ResponseEntity que indica el éxito de la eliminación del usuario si la operación es exitosa.
     */

    @DeleteMapping(value = "/eliminar/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
    	   usuarioService.eliminarUsuario(id);
	       ApiResponse<String> response = new ApiResponse<>(true, "Usuario eliminado con éxito", null);
	       return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /**
     * Método que maneja una solicitud GET para mostrar el artista de una canción en una lista de reproducción por un usuario específico.
     * 
     * @param idUsuario el identificador del usuario.
     * @param idListaReproduccion el identificador de la lista de reproducción.
     * @param idCancion el identificador de la canción.
     * @return ResponseEntity que contiene el artista de la canción en la lista de reproducción si se encuentra,
     *         o un ResponseEntity sin contenido si no se encuentra ningún artista asociado a la canción en la lista de reproducción.
     */

    @GetMapping(value = "/listar/{idUsuario}/lista-reproduccion/{idListaReproduccion}/canciones/{idCancion}/artista", headers = "X-VERSION=1")
    public ResponseEntity<?> mostrarArtistaporIdCancionporIdListaReproduccionporIdUsuario(@PathVariable Long idUsuario, @PathVariable Long idListaReproduccion, @PathVariable Long idCancion) {
    	Artista artista = usuarioService.mostrarArtistaporIdCancionporIdListaReproduccionporIdUsuario(idUsuario, idListaReproduccion, idUsuario);
    	ArtistaDTO artistaDTO = modelMapper.map(artista, ArtistaDTO.class);
    	if(artista == null){
    		return ResponseEntity.noContent().build();
    	}else {
    		
    		artistaDTO.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(artistaDTO.getIdArtista())).withSelfRel());
    		artistaDTO.add(linkTo(methodOn(UsuarioController.class).asignarListaReproduccion(idUsuario, idListaReproduccion)).withRel("asignacion"));
    		artistaDTO.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel(IanaLinkRelations.COLLECTION));
    		ApiResponse<Artista> response = new ApiResponse<>(true, "Artista de la canción en la lista de reproducción obtenido con éxito", artista);
            return ResponseEntity.ok(response);
    	}
    }
}