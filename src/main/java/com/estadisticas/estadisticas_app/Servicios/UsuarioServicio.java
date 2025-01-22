package com.estadisticas.estadisticas_app.Servicios;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estadisticas.estadisticas_app.Dtos.RegistroUsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepository usuarioRepository; // Inyectamos el repositorio para gestionar las operaciones con la base de datos
	@Autowired
	private PasswordEncoder passwordEncoder; // Inyectamos el PasswordEncoder para encriptar y comparar contraseñas
	@Autowired
    private EmailServicio emailServicio;
	
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
	    try {
	        // Validación de que no falten campos obligatorios
	        if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
	            throw new IllegalArgumentException("El email es obligatorio.");
	        }

	        // Verificar si el email ya existe
	        if (emailExistsUsuario(usuarioDto.getEmailUsuario())) {
	            throw new IllegalArgumentException("El email ya está registrado.");
	        }

	        // Crear un nuevo objeto Usuario con los datos del DTO
	        Usuario usuario = new Usuario();
	        usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
	        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
	        usuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
	        // Encriptar la contraseña antes de guardarla en la base de datos
	        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDto.getPasswordUsuario()));
	        // Asignar un rol por defecto ("usuario")
	        usuario.setRolUsuario("usuario");
	        // Generar un token de verificación y asignarlo al usuario
	        String token = generarTokenDeVerificacion();
	        usuario.setVerificacionToken(token);

	        // Guardar la foto si está presente
	        if (usuarioDto.getFotoUsuario() != null) {
	            usuario.setFotoUsuario(usuarioDto.getFotoUsuario());
	        }

	        // Guardar el nuevo usuario en la base de datos
	        usuarioRepository.save(usuario);

	        // Enviar correo de verificación
	        emailServicio.enviarCorreoVerificacion(usuarioDto.getEmailUsuario(), token);

	    } catch (Exception e) {
	        // Lógica para capturar y registrar excepciones
	        e.printStackTrace();
	        throw new RuntimeException("Hubo un problema al registrar el usuario: " + e.getMessage());
	    }
	}
	
	public void activarUsuario(String token) {
	    // Buscamos el usuario con el token proporcionado (asegurándonos de que es único)
	    Usuario usuario = usuarioRepository.findByVerificacionToken(token);
	    
	    if (usuario == null) {
	        throw new IllegalArgumentException("Token de verificación no válido.");
	    }

	    // Establecemos el estado del usuario como activo
	    usuario.setVerificado(true);

	    // Limpiamos el token ya que el usuario está verificado
	    usuario.setVerificacionToken(null);

	    // Guardamos la actualización en la base de datos
	    usuarioRepository.save(usuario);
	}
	
	
	
	public void eliminarUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + usuarioId);
        }
        usuarioRepository.deleteById(usuarioId);
    }
	public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
	public String generarTokenDeVerificacion() {
	    return UUID.randomUUID().toString();
	}
}
