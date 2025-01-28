package com.estadisticas.estadisticas_app.Controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroUsuarioDto usuarioDto) {
        try {
            if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email es obligatorio.");
            }

            if (usuarioServicio.emailExistsUsuario(usuarioDto.getEmailUsuario())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
            }

            usuarioServicio.registroUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
    // Endpoint para activar el usuario usando el token enviado por correo
    @GetMapping("/activar")
    public String activarCuenta(@RequestParam String token) {
        try {
            // Llamamos al servicio para activar el usuario
            usuarioServicio.activarUsuario(token);
            return "Cuenta activada exitosamente. Ahora puedes iniciar sesión.";
        } catch (Exception e) {
            return "Hubo un error al activar la cuenta: " + e.getMessage();
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
    @PostMapping("/solicitar-restablecimiento-contraseña")
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