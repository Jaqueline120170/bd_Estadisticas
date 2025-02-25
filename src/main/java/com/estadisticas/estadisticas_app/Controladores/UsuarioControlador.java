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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

 // Endpoint para reenviar enlace de activación de cuenta
    @PostMapping("/reenviar-enlace-activacion")
    public ResponseEntity<Map<String, String>> reenviarEnlaceActivacion(@RequestParam("email") String emailUsuario) {
        Map<String, String> response = new HashMap<>();
        try {
            usuarioServicio.reenviarEnlaceActivacion(emailUsuario);
            response.put("mensaje", "Se ha enviado un nuevo enlace de activación a tu correo.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
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
        Map<String, String> response = new HashMap<>();
        try {
            usuarioServicio.solicitarRestablecimientoContraseña(emailUsuario);
            response.put("mensaje", "Se ha enviado un enlace para restablecer tu contraseña al correo.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {  // Captura la excepción lanzada en el servicio
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Devuelve un 404
        } catch (Exception e) {
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

        try {
            // Llamamos al servicio para restablecer la contraseña
            usuarioServicio.restablecerContraseña(token, nuevaContraseña);

            // Retornar mensaje de éxito como JSON
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida con éxito."));
        } catch (IllegalArgumentException e) {
            // Retornar mensaje de error como JSON
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
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
    @PutMapping("/actualizar-rol-premier")
    public ResponseEntity<?> actualizarRolAPremier(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            usuarioServicio.actualizarRolAPremier(email);
            return ResponseEntity.ok(Map.of("message", "Rol actualizado a premier"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/{id}/suscripcion-premium")
    public ResponseEntity<?> activarSuscripcionPremium(@PathVariable Long id) {
        try {
            usuarioServicio.activarSuscripcionPremium(id);
            return ResponseEntity.ok("El usuario ahora es premium.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}