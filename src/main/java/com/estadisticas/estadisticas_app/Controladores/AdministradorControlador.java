package com.estadisticas.estadisticas_app.Controladores;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.estadisticas.estadisticas_app.Dtos.ListarUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.UsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;
import com.estadisticas.estadisticas_app.Servicios.AdministradorServicio;

/**
 * Controlador que maneja las solicitudes HTTP relacionadas con la administración de usuarios.
 * Solo los administradores pueden acceder a estas rutas, ya que están protegidas con seguridad basada en roles.
 */
@CrossOrigin(origins = "https://jaquedev.es")
@RestController
@RequestMapping("/api/admin")
public class AdministradorControlador {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);

    @Autowired
    private AdministradorServicio administradorServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepository;
    

    /**
     * Endpoint que lista todos los usuarios.
     * Solo accesible para administradores.
     * @return Lista de usuarios.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<ListarUsuarioDto>> listarUsuarios() {
        try {
            logger.info("El administrador solicitó la lista de usuarios.");
            List<ListarUsuarioDto> usuarios = administradorServicio.listarTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            logger.error("Error al listar usuarios: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .build(); // Error 500 sin body o puedes crear un body con mensaje si usas Map o DTO de error
        }
    }

    /**
     * Endpoint que permite modificar un usuario existente.
     * Solo accesible para administradores.
     * 
     * @param id ID del usuario a modificar.
     * @param usuarioActualizado Datos actualizados del usuario.
     * @param foto Imagen del usuario (opcional).
     * @return Usuario modificado o mensaje de error si ocurre un problema.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/modificar/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> modificarUsuario(@PathVariable Long id,
                                              @RequestPart("usuario") Usuario usuarioActualizado,
                                              @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            logger.info("El administrador está modificando el usuario con ID: {}", id);
            Usuario usuarioModificado = administradorServicio.modificarUsuario(id, usuarioActualizado, foto);
            return ResponseEntity.ok(usuarioModificado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al modificar usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint que elimina un usuario.
     * Solo accesible para administradores.
     * 
     * @param id ID del usuario a eliminar.
     * @return Mensaje de éxito o error si ocurre un problema.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            logger.info("El administrador está eliminando al usuario con ID: {}", id);
            administradorServicio.eliminarUsuario(id);
            return ResponseEntity.ok().body("{\"mensaje\": \"Usuario eliminado correctamente.\"}");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }catch (Exception e) {
            logger.error("Error inesperado al eliminar usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Error al eliminar usuario\"}");
        }
    }

    /**
     * Endpoint para buscar un usuario por su ID.
     * 
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado o mensaje de error si no existe.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Long id) {
        try {
            logger.info("El administrador está buscando el usuario con ID: {}", id);
            Usuario usuario = administradorServicio.buscarUsuarioPorId(id);
            UsuarioDto dto = new UsuarioDto(usuario);
            return ResponseEntity.ok(dto);  // Envía el DTO al frontend
            
        } catch (IllegalArgumentException e) {
            logger.warn("Intento de búsqueda fallido. Usuario con ID {} no encontrado.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"error\": \"Usuario no encontrado\"}");
        }
    }


    /**
     * Endpoint para obtener las estadísticas de usuarios.
     * Solo accesible para administradores.
     * 
     * @return Estadísticas de los usuarios en formato JSON.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estadisticas")
    public ResponseEntity<?> obtenerEstadisticasUsuarios() {
    	try {
        logger.info("El administrador ha solicitado las estadísticas de usuarios.");
        Map<String, Long> estadisticas = administradorServicio.obtenerEstadisticasUsuarios();
        return ResponseEntity.ok(estadisticas);
    } catch (Exception e) {
        logger.error("Error al obtener estadísticas de usuarios: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("{\"error\": \"Error al obtener estadísticas\"}");
    }
    }
    

}
