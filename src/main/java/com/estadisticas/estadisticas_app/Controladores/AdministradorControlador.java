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

import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Servicios.AdministradorServicio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdministradorControlador {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);

    @Autowired
    private AdministradorServicio administradorServicio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        logger.info("El administrador solicitó la lista de usuarios.");
        return ResponseEntity.ok(administradorServicio.listarTodosLosUsuarios());
    }

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
        }
    }
    /**
     * Buscar un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado o mensaje de error si no existe.
     */
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Long id) {
        try {
            logger.info("El administrador está buscando el usuario con ID: {}", id);
            Usuario usuario = administradorServicio.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            logger.warn("Intento de búsqueda fallido. Usuario con ID {} no encontrado.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"error\": \"Usuario no encontrado\"}");
        }
    }
    /**
     * Obtener estadísticas de usuarios.
     * Solo los administradores pueden acceder.
     * @return JSON con estadísticas de usuarios.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estadisticas")
    public ResponseEntity<?> obtenerEstadisticasUsuarios() {
        logger.info("El administrador ha solicitado las estadísticas de usuarios.");
        Map<String, Long> estadisticas = administradorServicio.obtenerEstadisticasUsuarios();
        return ResponseEntity.ok(estadisticas);
    }


}
