package com.estadisticas.estadisticas_app.Controladores;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
import com.estadisticas.estadisticas_app.Servicios.UsuarioServicio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

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
    public ResponseEntity<Map<String, String>> activarCuenta(@RequestParam String token) {
        System.out.println("Token recibido para activación: " + token);  // Log del token recibido

        Map<String, String> response = new HashMap<>();

        try {
            // Intenta activar la cuenta con el token proporcionado
            boolean activado = usuarioServicio.activarUsuario(token);

            if (activado) {
                response.put("message", "Cuenta activada exitosamente. Ahora puedes iniciar sesión.");
                return ResponseEntity.ok(response);  // Respuesta HTTP 200 (OK)
            } else {
                response.put("error", "Hubo un problema al activar la cuenta.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Respuesta HTTP 400 (Bad Request)
            }
        } catch (IllegalArgumentException e) {
            // Captura errores específicos de validación (ej. token inválido o expirado)
            System.err.println("Error al activar cuenta: " + e.getMessage()); // Log de error
            response.put("error", "Error al activar la cuenta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Respuesta HTTP 400 (Bad Request)
        } catch (Exception e) {
            // Captura cualquier otro error inesperado
            System.err.println("Error general: " + e.getMessage()); // Log de error general
            response.put("error", "Hubo un error al activar la cuenta. Inténtalo más tarde.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Respuesta HTTP 500 (Internal Server Error)
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
         // Devolver un token JWT (por ejemplo)
            String token = "jwt_token"; // Aquí deberías generar el token JWT

            // Opcional: Devuelve solo la información necesaria, como nombre y rol
            return ResponseEntity.ok("Inicio de sesión exitoso. Bienvenido, " + usuario.getNombreUsuario());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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