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
