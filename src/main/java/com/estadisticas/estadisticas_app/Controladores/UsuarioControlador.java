package com.estadisticas.estadisticas_app.Controladores;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.estadisticas.estadisticas_app.Dtos.ConsultaDto;
import com.estadisticas.estadisticas_app.Dtos.DescargaDto;
import com.estadisticas.estadisticas_app.Dtos.LoginUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.RegistroUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.UsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;
import com.estadisticas.estadisticas_app.Servicios.AdministradorServicio;
import com.estadisticas.estadisticas_app.Servicios.ConsultaServicio;
import com.estadisticas.estadisticas_app.Servicios.UsuarioServicio;
import com.estadisticas.estadisticas_app.Utils.ValidacionesUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
	
	  // 🔁 Inyectamos la URL del frontend desde application.properties
	@Value("${frontend.url}")
	private String frontendUrl;


    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepository; // Inyectamos el repositorio
    private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

    /**
     * Registra un nuevo usuario en la plataforma.
     *
     * @param usuarioDto Datos del usuario a registrar.
     * @return Respuesta HTTP:
     *         - 201 (CREATED): Usuario registrado exitosamente.
     *         - 400 (BAD REQUEST): Falta el email del usuario.
     *         - 409 (CONFLICT): Email ya registrado.
     *         - 500 (INTERNAL SERVER ERROR): Error inesperado.
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registroUsuario(@RequestBody RegistroUsuarioDto usuarioDto) {
    	   logger.info("Intento de registro de usuario con email: {}", usuarioDto.getEmailUsuario());
        try {
            if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
            	logger.warn("Intento de registro fallido: email vacío");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El email es obligatorio."));
            }

            if (usuarioServicio.emailExistsUsuario(usuarioDto.getEmailUsuario())) {
            	logger.warn("Intento de registro fallido: email ya registrado {}", usuarioDto.getEmailUsuario());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "El email ya está registrado."));
            }

            usuarioServicio.registroUsuario(usuarioDto);
            logger.info("Usuario registrado exitosamente: {}", usuarioDto.getEmailUsuario());
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Usuario registrado exitosamente."));
        } catch (Exception e) {
        	logger.error("Error al registrar usuario {}: {}", usuarioDto.getEmailUsuario(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Error interno del servidor."));
        }
    }

    /**
     * Activa la cuenta de un usuario con un token enviado por correo.
     *
     * @param idUsuario ID del usuario.
     * @param token     Token de activación.
     * @param response  Objeto para redireccionar al cliente.
     * @throws IOException Si ocurre un error al redirigir.
     */
    @GetMapping("/activar")
    public void activarCuenta(@RequestParam("id") Long idUsuario, @RequestParam("token") String token, HttpServletResponse response) throws IOException {
        logger.info("Solicitud de activación recibida para usuario ID: " + idUsuario);

        try {
            boolean activado = usuarioServicio.activarUsuario(token, idUsuario);
            
            if (activado) {
                logger.info("Cuenta activada correctamente. Redirigiendo a login.");
                response.sendRedirect(frontendUrl + "/login?mensaje=activacion_exitosa");
                //response.sendRedirect("http://localhost:4200/login?mensaje=activacion_exitosa");
            } else {
                logger.info("Cuenta ya estaba activada. Redirigiendo a login.");
                response.sendRedirect(frontendUrl + "/login?mensaje=ya_activada");
                //response.sendRedirect("http://localhost:4200/login?mensaje=ya_activada");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error al activar cuenta: " + e.getMessage());
            response.sendRedirect(frontendUrl + "/error-activacion?error=" + e.getMessage());
            //response.sendRedirect("http://localhost:4200/error-activacion?error=" + e.getMessage());
        }
    }

    /**
     * Reenvía el enlace de activación de cuenta a un usuario.
     *
     * @param emailUsuario Email del usuario.
     * @return Respuesta HTTP con mensaje de éxito o error.
     */
    @PostMapping("/reenviar-enlace-activacion")
    public ResponseEntity<Map<String, String>> reenviarEnlaceActivacion(@RequestParam("email") String emailUsuario) {
    	logger.info("Solicitud de reenvío de enlace de activación para: {}", emailUsuario);
        Map<String, String> response = new HashMap<>();
        try {
            usuarioServicio.reenviarEnlaceActivacion(emailUsuario);
            logger.info("Enlace de activación reenviado con éxito a: {}", emailUsuario);
            response.put("mensaje", "Se ha enviado un nuevo enlace de activación a tu correo.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
        	logger.warn("Error al reenviar enlace de activación: {}", e.getMessage());
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Endpoint para solicitar el restablecimiento de la contraseña
     * @param emailUsuario el email del usuario que solicita el restablecimiento
     * @return ResponseEntity con un mensaje de éxito o error
     */
    @PostMapping("/solicitar-restablecimiento-contrasena")
    public ResponseEntity<Map<String, String>> solicitarRestablecimientoContraseña(@RequestParam String emailUsuario) {
    	logger.info("Solicitud de restablecimiento de contraseña para: {}", emailUsuario);
        Map<String, String> response = new HashMap<>();
        try {
            usuarioServicio.solicitarRestablecimientoContraseña(emailUsuario);
            logger.info("Correo de restablecimiento enviado a: {}", emailUsuario);
            response.put("mensaje", "Se ha enviado un enlace para restablecer tu contraseña al correo.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {  
        	logger.warn("Solicitud de restablecimiento fallida: {}", e.getMessage());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); 
        } catch (Exception e) {
        	logger.error("Error inesperado en la solicitud de restablecimiento: {}", e.getMessage());
            response.put("error", "Error inesperado. Inténtalo más tarde.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Restablece la contraseña de un usuario utilizando un token de restablecimiento enviado por correo.
     * 
     * @param token Token único de restablecimiento
     * @param nuevaContraseña Nueva contraseña que el usuario desea establecer
     * @return ResponseEntity con un mensaje de éxito o error en formato JSON
     */
    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<Map<String, String>> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token"); // Obtenemos el token del cuerpo de la solicitud
        String nuevaContraseña = request.get("nuevaContraseña"); // Obtenemos la nueva contraseña
        logger.info("Intento de restablecimiento de contraseña con token: {}", token);
        try {
            // Llamamos al servicio para restablecer la contraseña
            usuarioServicio.restablecerContraseña(token, nuevaContraseña);
            logger.info("Contraseña restablecida con éxito para token: {}", token);

            // Retornar mensaje de éxito como JSON
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida con éxito."));
        } catch (IllegalArgumentException e) {
        	logger.warn("Restablecimiento de contraseña fallido: {}", e.getMessage());
            // Retornar mensaje de error como JSON
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    /**
     * Verifica la validez de un token de restablecimiento de contraseña.
     * 
     * @param token El token a verificar.
     * @return Respuesta HTTP con el estado del token.
     */
    @GetMapping("/verificar-token")
    public ResponseEntity<Map<String, String>> verificarToken(@RequestParam("token") String token) {
        logger.info("Recibiendo solicitud de verificación de token: {}", token); // 👀 Log para depuración

        Optional<Usuario> usuarioOptional = usuarioRepository.findByResetToken(token);

        if (usuarioOptional.isEmpty()) {
            logger.warn("Token inválido: {}", token); // 👀 Log de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token inválido o no encontrado."));
        }

        Usuario usuario = usuarioOptional.get();
        if (usuario.getResetTokenExpiracion().isBefore(LocalDateTime.now())) {
            logger.warn("Token expirado: {}", token); // 👀 Log de expiración
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El token de restablecimiento ha expirado."));
        }

        logger.info("Token válido: {}", token); // 👀 Log de éxito
        return ResponseEntity.ok(Map.of("mensaje", "Token válido."));
    }

    /**
     * Inicia sesión en la plataforma.
     *
     * @param loginDto Datos del usuario (email y contraseña).
     * @return Respuesta HTTP con información del usuario o error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginUsuarioDto loginDto) {
    	 logger.info("Intento de inicio de sesión para email: {}", loginDto.getEmailUsuario());
        try {
            Usuario usuario = usuarioServicio.login(loginDto);

            // Generar token de sesión (aquí podrías implementar JWT si quisieras)
            String token = UUID.randomUUID().toString(); // Simulación de token
            logger.info("Inicio de sesión exitoso para usuario: {}", loginDto.getEmailUsuario());
            // Devolver datos esenciales para el frontend
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inicio de sesión exitoso.");
            response.put("nombre", usuario.getNombreUsuario());
            response.put("rol", usuario.getRolUsuario());  // Puede ser "ADMIN" o "USUARIO"
            response.put("token", token);
            response.put("id", usuario.getIdUsuario()); // <--- Esto es lo importante


            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
        	logger.warn("Inicio de sesión fallido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
        	logger.warn("Intento de inicio de sesión inválido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    // Endpoint para obtener los datos del perfil del usuario autenticado
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
    	 logger.info("Solicitud para obtener usuario por ID: {}", id); 
        try {
            Usuario usuario = usuarioRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            UsuarioDto dto = new UsuarioDto(usuario);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
        	 logger.error("Error al obtener usuario por ID {}: {}", id, e.getMessage()); 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerfil(
            @PathVariable Long id,
            @RequestParam(required = false) String nombreUsuario,
            @RequestParam(required = false) String telefonoUsuario,
            @RequestParam(required = false) MultipartFile fotoUsuario) {
    	logger.info("Solicitud de actualización de perfil para ID: {}", id);
        try {
            usuarioServicio.actualizarUsuarioParcial(id, nombreUsuario, telefonoUsuario, fotoUsuario);
            logger.info("Perfil actualizado correctamente para ID: {}", id); 
            return ResponseEntity.ok(Map.of("mensaje", "Perfil actualizado correctamente"));
        } catch (RuntimeException e) {
        	logger.warn("No se encontró el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
        	logger.error("Error procesando imagen para ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen");
        }
    }
    /**
     * Permite a un usuario autenticado cambiar su contraseña desde su perfil.
     * 
     * @param idUsuario       ID del usuario autenticado.
     * @param request         Map con las claves: "contrasenaActual" y "nuevaContrasena".
     * @return ResponseEntity con mensaje de éxito o error.
     */
    @PutMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<Map<String, String>> cambiarContrasenaDesdePerfil(
            @PathVariable("id") Long idUsuario,
            @RequestBody Map<String, String> request) {
    	logger.info("Intento de cambio de contraseña desde perfil para ID: {}", idUsuario); 
        String contrasenaActual = request.get("contrasenaActual");
        String nuevaContrasena = request.get("nuevaContrasena");

        if (contrasenaActual == null || nuevaContrasena == null) {
        	logger.warn("Faltan campos para cambiar contraseña del ID: {}", idUsuario);
            return ResponseEntity.badRequest().body(Map.of("error", "Ambas contraseñas son obligatorias."));
        }

        try {
            usuarioServicio.cambiarContraseñaAutenticado(idUsuario, contrasenaActual, nuevaContrasena);
            logger.info("Contraseña actualizada exitosamente para ID: {}", idUsuario);
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
        } catch (IllegalArgumentException e) {
        	 logger.warn("Error de validación en cambio de contraseña para ID {}: {}", idUsuario, e.getMessage());  // 👈 NUEVO
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
        	 logger.error("Error inesperado cambiando contraseña para ID {}: {}", idUsuario, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al cambiar la contraseña."));
        }
    }

    /**
     * Recibe logs desde el frontend y los registra con el nivel adecuado.
     * 
     * @param request Contiene el nivel y el mensaje del log.
     * @return Respuesta con mensaje de éxito.
     */
    @PostMapping("/logs")
    public ResponseEntity<?> recibirLogDesdeFrontend(@RequestBody Map<String, String> request) {
        String nivel = request.get("nivel");  // Puede ser INFO, DEBUG, WARN, ERROR
        String mensaje = request.get("mensaje");

        if (nivel == null || mensaje == null) {
        	return ResponseEntity.badRequest().body(Map.of("error", "Nivel y mensaje son obligatorios."));
        }

        // Dependiendo del nivel de log, lo enviamos al logger adecuado
        switch (nivel.toUpperCase()) {
            case "DEBUG":
                logger.debug(mensaje);
                break;
            case "INFO":
                logger.info(mensaje);
                break;
            case "WARN":
                logger.warn(mensaje);
                break;
            case "ERROR":
                logger.error(mensaje);
                break;
            default:
                logger.info("Nivel desconocido: " + mensaje);
        }

        return ResponseEntity.ok(Map.of("message", "Log recibido y registrado"));

    }

}