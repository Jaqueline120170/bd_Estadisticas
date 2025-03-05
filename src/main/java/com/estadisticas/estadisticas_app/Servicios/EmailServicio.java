package com.estadisticas.estadisticas_app.Servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio para gestionar el envío de correos electrónicos en la aplicación.
 * Esta clase contiene métodos para enviar correos de verificación de cuenta y 
 * restablecimiento de contraseña, así como un método privado para enviar correos 
 * con soporte HTML.
 */
@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;  // Servicio de envío de correos electrónicos

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class); // Logger para registrar mensajes de error o información

    /**
     * Método para enviar un correo de verificación de cuenta.
     * Este correo incluye un enlace de activación con un token y el ID del usuario.
     * 
     * @param emailUsuario el correo electrónico del usuario.
     * @param token el token único de verificación.
     * @param idUsuario el ID del usuario para generar el enlace.
     */
    public void enviarCorreoVerificacion(String emailUsuario, String token, Long idUsuario) {
        // Construcción del enlace de verificación
        String enlaceVerificacion = "http://localhost:8081/api/usuarios/activar?id=" + idUsuario + "&token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("osteologia2@gmail.com");  // Correo del remitente
        message.setTo(emailUsuario);  // Correo del destinatario
        message.setSubject("Verificación de Correo");  // Asunto del correo
        message.setText("Por favor, verifica tu correo haciendo clic en el siguiente enlace: " + enlaceVerificacion);  // Cuerpo del correo

        try {
            mailSender.send(message);  // Envío del correo
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo de verificación: " + e.getMessage());  // Manejo de errores
        }
    }

    /**
     * Método para enviar un correo con un enlace de restablecimiento de contraseña.
     * 
     * @param emailUsuario el correo electrónico del usuario.
     * @param resetToken el token de restablecimiento de contraseña.
     */
    public void enviarCorreoRestablecerContraseña(String emailUsuario, String resetToken) {
        // Construcción del enlace para restablecer la contraseña
        String enlaceRestablecer = "http://localhost:4200/restablecer-contrasena/" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("osteologia2@gmail.com");  // Correo del remitente
        message.setTo(emailUsuario);  // Correo del destinatario
        message.setSubject("Restablece tu Contraseña");  // Asunto del correo
        message.setText("Haz clic en el siguiente enlace para restablecer tu contraseña: " + enlaceRestablecer);  // Cuerpo del correo

        try {
            mailSender.send(message);  // Envío del correo
        } catch (Exception e) {
            e.printStackTrace();  // Registro de error si no se pudo enviar el correo
            throw new RuntimeException("Error al enviar correo de restablecimiento de contraseña: " + e.getMessage());  // Manejo de errores
        }
    }
    
    /**
     * Método privado para enviar correos con soporte HTML.
     * 
     * @param destinatario el correo del destinatario.
     * @param asunto el asunto del correo.
     * @param cuerpo el cuerpo del correo en formato HTML.
     */
    private void enviarEmail(String destinatario, String asunto, String cuerpo) {
        MimeMessage mensaje = mailSender.createMimeMessage();  // Creación de un mensaje MIME (que permite HTML)

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);  // Habilitar soporte para mensajes con HTML
            helper.setTo(destinatario);  // Definir destinatario
            helper.setSubject(asunto);  // Definir asunto
            helper.setText(cuerpo, true);  // Definir cuerpo como HTML
            mailSender.send(mensaje);  // Envío del correo
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);  // Manejo de errores en el envío del correo HTML
        }
    }
}
