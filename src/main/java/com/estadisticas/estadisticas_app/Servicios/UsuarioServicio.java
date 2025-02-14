package com.estadisticas.estadisticas_app.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estadisticas.estadisticas_app.Dtos.LoginUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.RegistroUsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;
import com.estadisticas.estadisticas_app.Utils.ValidacionesUtil;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);
    @Autowired
    private UsuarioRepository usuarioRepository; // Inyectamos el repositorio
    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el PasswordEncoder
    @Autowired
    private EmailServicio emailServicio; // Inyectamos el servicio de email
    @Value("${token.expiration.minutes}")
    private int tokenExpirationMinutes; // Configurable desde application.properties

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
            	logger.warn("El email ya está registrado: {}", usuarioDto.getEmailUsuario());
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
            usuario.setTokenExpiracion(LocalDateTime.now().plusMinutes(tokenExpirationMinutes));

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
     * Método para activar un usuario mediante un token de verificación.
     * @param token El token de verificación enviado por email.
     * @return true si la activación fue exitosa, false en caso contrario.
     * @throws IllegalArgumentException Si el token es inválido o ha expirado.
     */
    @Transactional
    public boolean activarUsuario(String token) {
        if (token == null || token.trim().isEmpty()) {
            logger.warn("Intento de activación con token vacío.");
            throw new IllegalArgumentException("El token de activación no puede ser vacío.");
        }

        Usuario usuario = usuarioRepository.findByVerificacionToken(token);

        if (usuario == null) {
            logger.warn("Intento de activación con token inválido: " + token);
            throw new IllegalArgumentException("Token de verificación no válido.");
        }

        if (usuario.isVerificado()) {
            logger.info("El usuario con email " + usuario.getEmailUsuario() + " ya estaba activado.");
            return false; // Ya estaba activado
        }

        if (usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            logger.warn("El token ha expirado para el usuario: " + usuario.getEmailUsuario());
            throw new IllegalArgumentException("El token ha expirado.");
        }

        usuario.setVerificado(true);
        usuario.setTokenExpiracion(null);
        usuario.setVerificacionToken(null);
        usuarioRepository.save(usuario);

        logger.info("Cuenta activada con éxito para el usuario: " + usuario.getEmailUsuario());
        return true;
    }
    /**
     *Método para el Reenvio de enlace de verificacion al usuario, genera nuevo token de verificacion en caso de
     *que el token enviado al principio, haya expirado (15 minutos) @Value("${token.expiration.minutes}")
    private int tokenExpirationMinutes; // Configurable desde application.properties.
     * @param token el token de verificación enviado por email
     */
    public void reenviarEnlaceActivacion(String emailUsuario) {
        // Buscar el usuario por email, y si no existe, lanzar excepción
        Usuario usuario = usuarioRepository.findByEmailUsuario(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un usuario con ese email."));

        // Verificar si el usuario ya está verificado
        if (usuario.isVerificado()) {
            throw new IllegalArgumentException("La cuenta ya está activada.");
        }

        // Generar un nuevo token de verificación
        String nuevoToken = generarTokenDeVerificacion();
        usuario.setVerificacionToken(nuevoToken);
        usuario.setTokenExpiracion(LocalDateTime.now().plusMinutes(tokenExpirationMinutes));

        // Guardar el usuario con el nuevo token
        usuarioRepository.save(usuario);

        // Enviar el nuevo correo de verificación
        emailServicio.enviarCorreoVerificacion(usuario.getEmailUsuario(), nuevoToken);
    }
    /**
     * Método para solicitar el restablecimiento de la contraseña.
     * Genera un token de restablecimiento, lo asigna al usuario y envía un correo con el enlace para restablecerla.
     * @param emailUsuario el email del usuario que solicita el restablecimiento
     */
    public void solicitarRestablecimientoContraseña(String emailUsuario) {
        // Buscar al usuario por el email proporcionado
        Usuario usuario = usuarioRepository.findByEmailUsuario(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un usuario con ese email."));

        // Generar un token de restablecimiento único
        String resetToken = UUID.randomUUID().toString();
        usuario.setResetToken(resetToken);
        usuario.setResetTokenExpiracion(LocalDateTime.now().plusMinutes(tokenExpirationMinutes));  // Token válido por 30 minutos

        // Guardar el usuario con el nuevo token
        usuarioRepository.save(usuario);

        // Enviar el correo con el enlace de restablecimiento de la contraseña
        emailServicio.enviarCorreoRestablecerContraseña(usuario.getEmailUsuario(), resetToken);
    }
    /**
     * Metod para Restablecer la contraseña del usuario usando el token de restablecimiento.
     * @param token Token de restablecimiento
     * @param nuevaPassword Nueva contraseña que el usuario desea establecer
     */
    public void restablecerContraseña(String token, String nuevaContraseña) {
        // Buscar al usuario por el token de restablecimiento
        Optional<Usuario> usuarioOptional = usuarioRepository.findByResetToken(token);

        // Verificar si el usuario con ese token existe
        if (usuarioOptional.isPresent()) {
            // Obtener el usuario del Optional
            Usuario usuario = usuarioOptional.get();

            // Verificar si el token ha expirado
            if (usuario.getResetTokenExpiracion().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("El token de restablecimiento ha expirado.");
            }

            // Cambiar la contraseña del usuario
            usuario.setPasswordUsuario(passwordEncoder.encode(nuevaContraseña)); // Asegúrate de encriptar la nueva contraseña
            usuario.setResetToken(null); // Limpiar el resetToken
            usuario.setResetTokenExpiracion(null); // Limpiar el tiempo de expiración del resetToken

            // Guardar el usuario con la nueva contraseña
            usuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("Token de restablecimiento inválido.");
        }
    }

    /**
     * Método para Loggin Usuario
     * @param loginDto DTO con emailUsuario y passwordUsuario
     * @return Usuario autenticado
     */
    public Usuario login(LoginUsuarioDto loginDto) {
        // Validaciones
        ValidacionesUtil.validarNoVacio(loginDto.getEmailUsuario(), "emailUsuario");
        ValidacionesUtil.validarEmail(loginDto.getEmailUsuario());
        ValidacionesUtil.validarNoVacio(loginDto.getPasswordUsuario(), "passwordUsuario");
        ValidacionesUtil.validarPassword(loginDto.getPasswordUsuario());

        // Buscar usuario por email
        Usuario usuario = usuarioRepository
            .findByEmailUsuario(loginDto.getEmailUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ese email."));

        // Verificar si la cuenta ha sido activada mediante verificación de email
        if (!usuario.isVerificado()) {
            throw new IllegalStateException("La cuenta no ha sido verificada. Por favor revisa tu email.");
        }

        // Verificar la contraseña
        if (!passwordEncoder.matches(loginDto.getPasswordUsuario(), usuario.getPasswordUsuario())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return usuario; // Si pasa todo, retorna el usuario autenticado
    }

    /**
     * Método que Genera un token de verificación único.
     * @return token único generado
     */
    public String generarTokenDeVerificacion() {
        return UUID.randomUUID().toString();
    }
}
