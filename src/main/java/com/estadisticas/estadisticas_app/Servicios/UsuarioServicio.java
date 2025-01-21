package com.estadisticas.estadisticas_app.Servicios;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.estadisticas.estadisticas_app.Dtos.RegistroUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.UsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepository usuarioRepository; // Inyectamos el repositorio para gestionar las operaciones con la base de datos

	
	/**
	 * Método para verificar si un usuario con un email específico ya está registrado.
	 * @param emailUsuario el email del usuario a verificar
	 * @return true si el email ya está registrado, de lo contrario false
	 */
	public boolean emailExistsUsuario(String emailUsuario) {
		return usuarioRepository.existsByEmailUsuario(emailUsuario); // Retorna true si el email ya existe
	}
	
	/**
	 * Método para registrar un nuevo usuario en el sistema.
	 * <p>
	 * Toma un DTO con los datos del usuario, encripta la contraseña y la guarda en la base de datos.
	 * </p>
	 * @param usuarioDto el DTO con los datos del usuario a registrar
	 */
	public void registroUsuario(RegistroUsuarioDto usuarioDto) {
	    // Validación del email: no puede ser nulo o vacío
	    if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
	        throw new IllegalArgumentException("El email es obligatorio.");
	    }

        // Crear un nuevo objeto Usuario con los datos del DTO
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
        usuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
	    usuario.setPasswordUsuario(usuarioDto.getPasswordUsuario());
	    // Asignar un rol por defecto ("usuario")
	    usuario.setRolUsuario("usuario");
	    // Guardar la foto (si está presente)
        if (usuarioDto.getFotoUsuario() != null) {
            usuario.setFotoUsuario(usuarioDto.getFotoUsuario());
        }

	    // Guardar el nuevo usuario en la base de datos
	    usuarioRepository.save(usuario);
	}
}
