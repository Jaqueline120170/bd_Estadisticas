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
     * Verifica si un usuario con el email dado ya está registrado.
     *
     * @param emailUsuario Email del usuario a verificar.
     * @return true si el email ya está registrado, de lo contrario false.
     */
    public boolean emailExistsUsuario(String emailUsuario) {
        return usuarioRepository.existsByEmailUsuario(emailUsuario); // Retorna true si el email ya existe
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * Aplica validaciones y encripta la contraseña antes de guardar el usuario.
     * 
     * @param usuarioDto Datos del usuario a registrar.
     * @throws RuntimeException Si ocurre un error durante el registro.
     */
    public void registroUsuario(RegistroUsuarioDto usuarioDto) {
        try {
        	 logger.info("Validando datos para el registro de {}", usuarioDto.getEmailUsuario());
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
            usuario.setRolUsuario("USUARIO");
            // Generar un token de verificación y asignarlo al usuario
            String token = generarTokenDeVerificacion();
            usuario.setVerificacionToken(token);
            usuario.setTokenExpiracion(LocalDateTime.now().plusMinutes(tokenExpirationMinutes));

            // Guardar el nuevo usuario en la base de datos
            usuarioRepository.save(usuario);
            
            logger.info("Usuario registrado con éxito: {}", usuarioDto.getEmailUsuario());

            // Enviar correo de verificación
            emailServicio.enviarCorreoVerificacion(usuario.getEmailUsuario(), token, usuario.getIdUsuario());


        } catch (Exception e) {
        	logger.error("Error al registrar el usuario: {}", e.getMessage(), e);  // El 'e' aquí es la excepción completa
            // Lógica para capturar y registrar excepciones
            e.printStackTrace();
            throw new RuntimeException("Hubo un problema al registrar el usuario: " + e.getMessage());
        }
    }

    /**
     * Activa la cuenta de un usuario verificando su token de activación.
     *
     * @param token Token de verificación enviado por email.
     * @param idUsuario ID del usuario a activar.
     * @return true si la activación fue exitosa, false si ya estaba activado.
     * @throws IllegalArgumentException Si el token es inválido o ha expirado.
     */
    @Transactional
    public boolean activarUsuario(String token, Long idUsuario) {
        logger.info("Intentando activar usuario con token: " + token);

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.isVerificado()) {
            logger.info("El usuario " + usuario.getEmailUsuario() + " ya está activado.");
            return false; // Indica que ya estaba activado
        }

        if (!token.equals(usuario.getVerificacionToken())) {
            logger.warn("Token incorrecto para el usuario: " + usuario.getEmailUsuario());
            throw new IllegalArgumentException("Token de verificación no válido.");
        }

        if (usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            logger.warn("Token expirado para el usuario: " + usuario.getEmailUsuario());
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
     * Reenvía el enlace de activación generando un nuevo token.
     *
     * @param emailUsuario Email del usuario al que se le enviará el enlace.
     * @throws IllegalArgumentException Si el usuario no existe o ya está activado.
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
        emailServicio.enviarCorreoVerificacion(usuario.getEmailUsuario(), nuevoToken, usuario.getIdUsuario());
        logger.info("Nuevo enlace de activación enviado a {}", emailUsuario);
    }
    /**
     * Solicita el restablecimiento de contraseña y envía un correo con un enlace.
     *
     * @param emailUsuario Email del usuario.
     * @throws IllegalArgumentException Si el usuario no existe.
     */
    public void solicitarRestablecimientoContraseña(String emailUsuario) {
        // Buscar al usuario por email
        Usuario usuario = usuarioRepository.findByEmailUsuario(emailUsuario)
            .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con ese email.")); // Cambiado a IllegalArgumentException

        // Generar token de restablecimiento
        String resetToken = UUID.randomUUID().toString();
        usuario.setResetToken(resetToken);
        usuario.setResetTokenExpiracion(LocalDateTime.now().plusMinutes(tokenExpirationMinutes));

        // Guardar usuario con nuevo token
        usuarioRepository.save(usuario);

        // Enviar el correo con el enlace de restablecimiento
        emailServicio.enviarCorreoRestablecerContraseña(usuario.getEmailUsuario(), resetToken);
        logger.info("Solicitud de restablecimiento enviada a {}", emailUsuario);
    }

    /**
     * Restablece la contraseña de un usuario usando un token de restablecimiento.
     *
     * @param token Token de restablecimiento.
     * @param nuevaContraseña Nueva contraseña del usuario.
     * @throws IllegalArgumentException Si el token es inválido o ha expirado.
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
            logger.info("Contraseña restablecida correctamente para {}", usuario.getEmailUsuario());
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
    	 logger.info("Autenticando usuario {}", loginDto.getEmailUsuario());
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
        	logger.warn("Intento de login fallido para {}", loginDto.getEmailUsuario());
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
        logger.info("Usuario {} autenticado correctamente", loginDto.getEmailUsuario());
        return usuario; // Si pasa todo, retorna el usuario autenticado
    }
    
    public void actualizarRolAPremier(String email) {
        Usuario usuario = usuarioRepository
            .findByEmailUsuario(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setRolUsuario("premier");
        
        usuarioRepository.save(usuario);
    }


    /**
     * Método que Genera un token de verificación único.
     * @return token único generado
     */
    public String generarTokenDeVerificacion() {
        return UUID.randomUUID().toString();
    }

	public void activarSuscripcionPremium(Long id) {
		// TODO Auto-generated method stub
		
	}
}