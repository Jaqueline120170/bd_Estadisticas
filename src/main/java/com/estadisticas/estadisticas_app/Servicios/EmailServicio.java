package com.estadisticas.estadisticas_app.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;
    
    //Método para enviar un correo de verificación de cuenta y poder activarla para
    //poder loggearse posteriormente
    public void enviarCorreoVerificacion(String emailUsuario, String token) {
    	String enlaceVerificacion = "http://localhost:8081/api/usuarios/activar?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("osteologia2@gmail.com");
        message.setTo(emailUsuario);
        message.setSubject("Verificación de Correo");
        message.setText("Por favor, verifica tu correo haciendo clic en el siguiente enlace: " + enlaceVerificacion);
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar correo de verificación: " + e.getMessage());
        }
    }
 // Método para enviar correo con enlace de restablecimiento de contraseña
 // Método para enviar el correo de restablecimiento de contraseña
    public void enviarCorreoRestablecerContraseña(String emailUsuario, String resetToken) {
        String enlaceRestablecer = "http://localhost:8081/api/usuarios/restablecer-contraseña?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("osteologia2@gmail.com");
        message.setTo(emailUsuario);
        message.setSubject("Restablece tu Contraseña");
        message.setText("Haz clic en el siguiente enlace para restablecer tu contraseña: " + enlaceRestablecer);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar correo de restablecimiento de contraseña: " + e.getMessage());
        }
    }
    
    /**
     * Método privado para enviar correos con soporte HTML (opcional)
     */
    private void enviarEmail(String destinatario, String asunto, String cuerpo) {
        MimeMessage mensaje = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo, true); // El segundo parámetro indica que es HTML
            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}
