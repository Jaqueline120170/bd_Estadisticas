package com.estadisticas.estadisticas_app.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;
import com.estadisticas.estadisticas_app.Servicios.AdministradorServicio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdministradorControlador {
	
	@Autowired
	private AdministradorServicio administradorServicio;
	
	 // Solo los administradores pueden listar usuarios, se emplean estas etiquetas de seguridad adicional para que
	//ninguna persona que tenga esa URL y que no sea admin, pueda modificar  el LocalStorage y manipular los datos
	//de la sesion para acceder a ella
	 /**
     * Método para listar todos los usuarios.
     * Solo los administradores pueden acceder.
     */
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> listarUsuarios() {
	    List<Usuario> usuarios = administradorServicio.listarTodosLosUsuarios();
	    return ResponseEntity.ok(usuarios);
	}
    /**
     * Modificar un usuario. Solo el administrador puede modificar usuarios.
     */
    // Solo los administradores pueden modificar usuarios
    @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/modificar/{id}")
	public ResponseEntity<?> modificarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
	    try {
	        Usuario usuarioModificado = administradorServicio.modificarUsuario(id, usuarioActualizado);
	        return ResponseEntity.ok(usuarioModificado);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
    /**
     * Eliminar un usuario. Solo el administrador puede eliminar usuarios.
     */
 // Solo los administradores pueden eliminar usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            administradorServicio.eliminarUsuario(id);
            // Cambiar el mensaje de texto por un JSON
            return ResponseEntity.ok().body("{\"mensaje\": \"Usuario eliminado correctamente.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    /**
     * Obtener estadísticas de usuarios. Solo los administradores pueden verlas.
     */
 // Solo los administradores pueden obtener estadísticas
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/estadisticas")
	public ResponseEntity<?> obtenerEstadisticasUsuarios() {
	    return ResponseEntity.ok(administradorServicio.obtenerEstadisticasUsuarios());
	}


}
