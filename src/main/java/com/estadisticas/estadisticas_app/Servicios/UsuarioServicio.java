package com.estadisticas.estadisticas_app.Servicios;

import java.util.List;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estadisticas.estadisticas_app.Dtos.LoginUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.RegistroUsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;
import com.estadisticas.estadisticas_app.Utils.ValidacionesUtil;

@Service
public class UsuarioServicio {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
    @Autowired
    private UsuarioRepository usuarioRepository; // Inyectamos el repositorio
    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el PasswordEncoder
    @Autowired
    private EmailServicio emailServicio; // Inyectamos el servicio de email

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
     * Toma un DTO con los datos del usuario, encripta la contraseña y la guarda en la base de datos.
     * @param usuarioDto el DTO con los datos del usuario a registrar
     */
    public void registroUsuario(RegistroUsuarioDto usuarioDto) {
        try {
            // Validaciones
            ValidacionesUtil.validarNoVacio(usuarioDto.getNombreUsuario(), "Nombre de Usuario");
            ValidacionesUtil.validarEmail(usuarioDto.getEmailUsuario());
            ValidacionesUtil.validarPassword(usuarioDto.getPasswordUsuario());

            // Verificar si el email ya está registrado
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
            
            logger.info("Usuario registrado con éxito: {}", usuarioDto.getEmailUsuario());

            // Enviar correo de verificación
            emailServicio.enviarCorreoVerificacion(usuarioDto.getEmailUsuario(), token);

        } catch (Exception e) {
        	logger.error("Error al registrar el usuario: {}", e.getMessage(), e);  // El 'e' aquí es la excepción completa
            // Lógica para capturar y registrar excepciones
            e.printStackTrace();
            throw new RuntimeException("Hubo un problema al registrar el usuario: " + e.getMessage());
        }
    }

    /**
     * Activar usuario mediante token de verificación.
     * @param token el token de verificación enviado por email
     */
    public void activarUsuario(String token) {
        // Buscar usuario con el token proporcionado
        Usuario usuario = usuarioRepository.findByVerificacionToken(token);

        if (usuario == null) {
            throw new IllegalArgumentException("Token de verificación no válido.");
        }

        // Activar cuenta del usuario
        usuario.setVerificado(true);
        usuario.setVerificacionToken(null); // Limpiar el token
        usuarioRepository.save(usuario);
    }
    /**
     * Loggin Usuario
     * @param loginDto DTO con emailUsuario y passwordUsuario
     * @return Usuario autenticado
     */
    public Usuario login(LoginUsuarioDto loginDto) {
        // Validar que el email no esté vacío y tenga un formato correcto
        ValidacionesUtil.validarNoVacio(loginDto.getEmailUsuario(), "emailUsuario");
        ValidacionesUtil.validarEmail(loginDto.getEmailUsuario());

        // Validar que la contraseña no esté vacía y cumpla con las reglas mínimas
        ValidacionesUtil.validarNoVacio(loginDto.getPasswordUsuario(), "passwordUsuario");
        ValidacionesUtil.validarPassword(loginDto.getPasswordUsuario());

        // Buscar el usuario por email
        Usuario usuario = usuarioRepository
            .findByEmailUsuario(loginDto.getEmailUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ese email."));

        // Verificar si la contraseña proporcionada coincide con la almacenada
        if (!passwordEncoder.matches(loginDto.getPasswordUsuario(), usuario.getPasswordUsuario())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        // Verificar si la cuenta ha sido activada mediante verificación de email
        if (!usuario.isVerificado()) {
            throw new IllegalStateException("La cuenta no ha sido verificada. Por favor revisa tu email.");
        }

        return usuario; // Retornar el usuario autenticado si pasa todas las validaciones
    }

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

    /**
     * Generar un token de verificación único.
     * @return token único generado
     */
    public String generarTokenDeVerificacion() {
        return UUID.randomUUID().toString();
    }
}
