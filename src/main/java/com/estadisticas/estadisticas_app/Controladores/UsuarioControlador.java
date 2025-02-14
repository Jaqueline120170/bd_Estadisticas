package com.estadisticas.estadisticas_app.Controladores;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estadisticas.estadisticas_app.Dtos.LoginUsuarioDto;
import com.estadisticas.estadisticas_app.Dtos.RegistroUsuarioDto;
import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;
import com.estadisticas.estadisticas_app.Servicios.UsuarioServicio;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private UsuarioRepository usuarioRepository; // Inyectamos el repositorio
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param usuarioDto Objeto que contiene la información del usuario a registrar.
     * @return Una respuesta HTTP con el resultado del registro:
     *         <ul>
     *         <li>201 (CREATED): Usuario registrado exitosamente.</li>
     *         <li>400 (BAD REQUEST): El email del usuario es obligatorio.</li>
     *         <li>409 (CONFLICT): El email ya está registrado.</li>
     *         <li>500 (INTERNAL SERVER ERROR): Error interno del servidor.</li>
     *         </ul>
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registroUsuario(@RequestBody RegistroUsuarioDto usuarioDto) {
        try {
            if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El email es obligatorio."));
            }

            if (usuarioServicio.emailExistsUsuario(usuarioDto.getEmailUsuario())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "El email ya está registrado."));
            }

            usuarioServicio.registroUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Usuario registrado exitosamente."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Error interno del servidor."));
        }
    }
    /**
     * Activa la cuenta de un usuario utilizando un token de verificación enviado por correo.
     * 
     * @param token Token único de activación
     * @return ResponseEntity con un mensaje de éxito o error en formato JSON
     */
    @GetMapping("/activar")
    public void activarCuenta(@RequestParam("id") Long idUsuario, @RequestParam("token") String token, HttpServletResponse response) throws IOException {
        logger.info("Solicitud de activación recibida para usuario ID: " + idUsuario);

        try {
            boolean activado = usuarioServicio.activarUsuario(token, idUsuario);
            
            if (activado) {
                logger.info("Cuenta activada correctamente. Redirigiendo a login.");
                response.sendRedirect("http://localhost:4200/login?mensaje=activacion_exitosa");
            } else {
                logger.info("Cuenta ya estaba activada. Redirigiendo a login.");
                response.sendRedirect("http://localhost:4200/login?mensaje=ya_activada");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error al activar cuenta: " + e.getMessage());
            response.sendRedirect("http://localhost:4200/error-activacion?error=" + e.getMessage());
        }
    }

    // Endpoint para reenviar enlace de activacion de cuenta
    @PostMapping("/reenviar-enlace-activacion")
    public ResponseEntity<String> reenviarEnlaceActivacion(@RequestParam("email") String emailUsuario) {
        try {
            usuarioServicio.reenviarEnlaceActivacion(emailUsuario);
            return ResponseEntity.ok("Se ha enviado un nuevo enlace de activación a tu correo.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Endpoint para loggin
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginUsuarioDto loginDto) {
        try {
            Usuario usuario = usuarioServicio.login(loginDto);

            // Generar token de sesión (aquí podrías implementar JWT si quisieras)
            String token = UUID.randomUUID().toString(); // Simulación de token

            // Devolver datos esenciales para el frontend
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inicio de sesión exitoso.");
            response.put("nombre", usuario.getNombreUsuario());
            response.put("rol", usuario.getRolUsuario());  // Puede ser "ADMIN" o "USUARIO"
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint para solicitar el restablecimiento de la contraseña
     * @param emailUsuario el email del usuario que solicita el restablecimiento
     * @return ResponseEntity con un mensaje de éxito o error
     */
    @PostMapping("/solicitar-restablecimiento-contrasena")
    public ResponseEntity<String> solicitarRestablecimientoContraseña(@RequestParam String emailUsuario) {
        try {
            // Llamamos al servicio para solicitar el restablecimiento de contraseña
            usuarioServicio.solicitarRestablecimientoContraseña(emailUsuario);

            return ResponseEntity.ok("Se ha enviado un enlace para restablecer tu contraseña al correo.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
 // Endpoint para restablecer la contraseña
    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<String> restablecerContrasena(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token"); // Obtenemos el token del cuerpo de la solicitud
            String nuevaContraseña = request.get("nuevaContraseña"); // Obtenemos la nueva contraseña

            // Llamamos al servicio para restablecer la contraseña
            usuarioServicio.restablecerContraseña(token, nuevaContraseña);

            return ResponseEntity.ok("Contraseña restablecida con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}