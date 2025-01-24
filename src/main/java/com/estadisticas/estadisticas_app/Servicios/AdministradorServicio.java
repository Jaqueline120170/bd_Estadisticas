package com.estadisticas.estadisticas_app.Servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;



public class AdministradorServicio {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
    @Autowired
    private UsuarioRepository usuarioRepository; // Inyectamos el repositorio
	
	 /**
     * Eliminar un usuario por ID.
     * @param usuarioId ID del usuario a eliminar
     */
    public void eliminarUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + usuarioId);
        }
        usuarioRepository.deleteById(usuarioId);
    }

    /**
     * Listar todos los usuarios registrados en el sistema.
     * @return lista de usuarios
     */
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

}
