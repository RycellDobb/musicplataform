package com.dagarcvj.music.plataform.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dagarcvj.music.plataform.domain.Artista;
import com.dagarcvj.music.plataform.domain.ListaReproduccion;
import com.dagarcvj.music.plataform.dto.ArtistaDTO;
import com.dagarcvj.music.plataform.dto.ListaReproduccionDTO;
import com.dagarcvj.music.plataform.services.ListaReproduccionService;
import com.dagarcvj.music.plataform.util.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
/**
 * 
 * @file: ListaReproduccionController.java
 * @author: (c)2024 Andy
 * @created: 1 mar 2024, 1:05:10
 *
 */
@RestController
@RequestMapping("/api/listasReproduccion")
public class ListaReproduccionController {
    @Autowired
    private ListaReproduccionService listaReproduccionService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método que maneja una solicitud GET para listar todas las listas de reproducción.
     * 
     * @return ResponseEntity que contiene una lista de listas de reproducción en forma de ListaReproduccionDTOs si se encuentran,
     *         o un ResponseEntity sin contenido si no se encuentran listas de reproducción.
     */

    @GetMapping(value = "/listar", headers = "X-VERSION=1")
    public ResponseEntity<?> listarListasReproduccion() {
        List<ListaReproduccion> listasReproduccion = listaReproduccionService.listarListasReproduccion();
        if(listasReproduccion == null || listasReproduccion.isEmpty()) {
        	return ResponseEntity.noContent().build();
        } else {
			List<ListaReproduccionDTO> listaReproduccionDTOs = listasReproduccion.stream()
					.map(listaReproduccion -> modelMapper.map(listaReproduccion, ListaReproduccionDTO.class))
					.collect(Collectors.toList());
			
			for(ListaReproduccionDTO listaR : listaReproduccionDTOs) {
				listaR.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(listaR.getIdLista())).withSelfRel());
				listaR.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
			}
			ApiResponse<List<ListaReproduccionDTO>> response = new ApiResponse<>(true, "Lista de listas de reproducción obtenida con éxito", listaReproduccionDTOs);
			return ResponseEntity.ok(response);
        }
    }

    /**
     * Método que maneja una solicitud GET para obtener una lista de reproducción por su identificador.
     * 
     * @param id el identificador de la lista de reproducción que se desea obtener.
     * @return ResponseEntity que contiene la lista de reproducción con el identificador especificado en forma de ListaReproduccionDTO si se encuentra,
     *         o un ResponseEntity sin contenido si no se encuentra ninguna lista de reproducción con el identificador especificado.
     */

    @GetMapping(value = "/listar/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> obtenerListaReproduccionPorId(@PathVariable Long id) {
	    ListaReproduccion listaReproduccion = listaReproduccionService.buscarPorIdListaReproduccion(id);
	    ListaReproduccionDTO  listaReproduccionDTO = modelMapper.map(listaReproduccion, ListaReproduccionDTO.class);
	    listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(listaReproduccionDTO.getIdLista())).withSelfRel());
	    listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
	    ApiResponse <ListaReproduccionDTO> response = new ApiResponse<>(true, "Lista de reproducción obtenida con éxito", listaReproduccionDTO);
		return ResponseEntity.ok(response);
	}

    /**
     * Método que maneja una solicitud POST para crear una nueva lista de reproducción.
     * 
     * @param listaReproduccionDTO la representación DTO de la lista de reproducción que se desea crear.
     * @return ResponseEntity que indica el éxito de la creación de la lista de reproducción junto con la representación DTO de la lista de reproducción creada si la operación es exitosa.
     */

    @PostMapping(value = "/crear", headers = "X-VERSION=1")
	public ResponseEntity<?> crearListaReproduccion(@Valid @RequestBody ListaReproduccionDTO listaReproduccionDTO) {
		ListaReproduccion  listaReproduccion = modelMapper.map(listaReproduccionDTO, ListaReproduccion.class);
		listaReproduccionService.grabarListaReproduccion(listaReproduccion);
		ListaReproduccionDTO listaReproduccionDTOCreado = modelMapper.map(listaReproduccion , ListaReproduccionDTO.class);
		
		listaReproduccionDTOCreado.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(listaReproduccionDTOCreado.getIdLista())).withSelfRel());
		listaReproduccionDTOCreado.add(linkTo(methodOn(ListaReproduccionController.class).crearListaReproduccion(listaReproduccionDTOCreado)).withRel("ListaReproduccion Creada"));
		listaReproduccionDTOCreado.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
	    ApiResponse <ListaReproduccionDTO> response =new ApiResponse<>(true, "Lista de reproducción creada con éxito", listaReproduccionDTOCreado);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
    /**
     * Método que maneja una solicitud PUT para editar una lista de reproducción existente.
     * 
     * @param listaReproduccionDTO la representación DTO actualizada de la lista de reproducción.
     * @param id el identificador de la lista de reproducción que se desea editar.
     * @return ResponseEntity que indica el éxito de la actualización de la lista de reproducción junto con la representación DTO de la lista de reproducción actualizada si la operación es exitosa.
     */

    @PutMapping(value = "/editar/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> editarListaReproduccion(@Valid @RequestBody ListaReproduccionDTO listaReproduccionDTO, @PathVariable Long id) {
		ListaReproduccion  listaReproduccion = modelMapper.map(listaReproduccionDTO, ListaReproduccion.class);
		listaReproduccionService.actualizarListaReproduccion(id, listaReproduccion);
		ListaReproduccionDTO listaReproduccionDTOActualizada = modelMapper.map(listaReproduccion, ListaReproduccionDTO.class);
		
		listaReproduccionDTOActualizada.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(listaReproduccionDTOActualizada.getIdLista())).withSelfRel());
		listaReproduccionDTOActualizada.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
	    ApiResponse <ListaReproduccionDTO> response =new ApiResponse<>(true, "Lista de reproducción actualizada con éxito", listaReproduccionDTOActualizada);
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	} 
    
    @PutMapping(value = "/editar/{idListaReproduccion}/agregar-cancion/{idCancion}", headers = "X-VERSION=1")
    public ResponseEntity<?> agregarCancion(@PathVariable Long idListaReproduccion, @PathVariable Long idCancion) {
    	ListaReproduccion listaReproduccion = listaReproduccionService.asignarCancion(idListaReproduccion, idCancion);
        ListaReproduccionDTO listaReproduccionDTO = modelMapper.map(listaReproduccion, ListaReproduccionDTO.class);
        
        listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(idListaReproduccion)).withSelfRel());
        listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
        listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).agregarCancion(idListaReproduccion, idCancion)).withRel("asignacion"));
        ApiResponse<ListaReproduccionDTO> response = new ApiResponse<>(true, "Cancion agregada con éxito a la lista de reproducción", listaReproduccionDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Método que maneja una solicitud PUT para agregar una canción a una lista de reproducción existente.
     * 
     * @param idListaReproduccion el identificador de la lista de reproducción a la que se desea agregar la canción.
     * @param idCancion el identificador de la canción que se desea agregar a la lista de reproducción.
     * @return ResponseEntity que indica el éxito de la adición de la canción a la lista de reproducción junto con la representación DTO de la lista de reproducción actualizada si la operación es exitosa.
     */

    @PutMapping(value = "/editar/{idListaReproduccion}/remover-cancion/{idCancion}", headers = "X-VERSION=1")
    public ResponseEntity<?> removerCancion(@PathVariable Long idListaReproduccion, @PathVariable Long idCancion) {
        ListaReproduccion listaReproduccion = listaReproduccionService.removerCancionDeLista(idListaReproduccion, idCancion);
        ListaReproduccionDTO listaReproduccionDTO = modelMapper.map(listaReproduccion, ListaReproduccionDTO.class);
        
        listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(idListaReproduccion)).withSelfRel());
        listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
        listaReproduccionDTO.add(linkTo(methodOn(ListaReproduccionController.class).removerCancion(idListaReproduccion, idCancion)).withRel("remover"));
        ApiResponse<ListaReproduccionDTO> response = new ApiResponse<>(true, "Canción removida con éxito de la lista de reproducción", listaReproduccionDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Método que maneja una solicitud DELETE para eliminar una lista de reproducción por su identificador.
     * 
     * @param id el identificador de la lista de reproducción que se desea eliminar.
     * @return ResponseEntity que indica el éxito de la eliminación de la lista de reproducción si la operación es exitosa.
     */

    @DeleteMapping(value = "/eliminar/{id}", headers = "X-VERSION=1")
    public ResponseEntity<?> eliminarListaReproduccion(@PathVariable Long id) {
 	   listaReproduccionService.eliminarListaReproduccion(id);
 	   ApiResponse<String> response = new ApiResponse<>(true, "Lista de reproduccion eliminada con éxito", null);
 	   return ResponseEntity.status(HttpStatus.OK).body(response);
 	}
    
    /**
     * Método que maneja una solicitud GET para mostrar el artista de una canción en una lista de reproducción.
     * 
     * @param idListaReproduccion el identificador de la lista de reproducción.
     * @param idCancion el identificador de la canción.
     * @return ResponseEntity que contiene el artista de la canción en la lista de reproducción si se encuentra,
     *         o un ResponseEntity sin contenido si no se encuentra ningún artista asociado a la canción en la lista de reproducción.
     */

    @GetMapping("/{idListaReproduccion}/canciones/{idCancion}/artista")
    public ResponseEntity<?> mostrarArtistaPorCancionEnListaReproduccion(@PathVariable Long idListaReproduccion, @PathVariable Long idCancion) {
    	Artista artista = listaReproduccionService.mostrarArtistaPorIdCancionPorIdListaReproduccion(idListaReproduccion, idCancion);
    	ArtistaDTO artistaDTO = modelMapper.map(artista, ArtistaDTO.class);
    	if (artista == null) {
    		return ResponseEntity.noContent().build();
    	} else {
    		
    		artistaDTO.add(linkTo(methodOn(ListaReproduccionController.class).obtenerListaReproduccionPorId(idListaReproduccion)).withSelfRel());
    		artistaDTO.add(linkTo(methodOn(ListaReproduccionController.class).listarListasReproduccion()).withRel(IanaLinkRelations.COLLECTION));
    		artistaDTO.add(linkTo(methodOn(ListaReproduccionController.class).mostrarArtistaPorCancionEnListaReproduccion(idListaReproduccion, idCancion)).withRel("mostraArtista"));

    		ApiResponse<Artista> response = new ApiResponse<>(true, "Artista de la canción en la lista de reproducción obtenido con éxito", artista);
            return ResponseEntity.ok(response);
    	}
    }
    
}
