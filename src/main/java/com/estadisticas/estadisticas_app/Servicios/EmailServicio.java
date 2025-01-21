package com.estadisticas.estadisticas_app.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailServicio {
	
	 @Autowired
	    private JavaMailSender mailSender;

	    /**
	     * Método para enviar un correo electrónico.
	     *
	     * @param destinatario El email del destinatario.
	     * @param asunto       El asunto del correo.
	     * @param contenido    El contenido del correo (puede incluir HTML).
	     */
	    public void enviarEmail(String destinatario, String asunto, String contenido) {
	        MimeMessage mensaje = mailSender.createMimeMessage();

	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
	            helper.setTo(destinatario);
	            helper.setSubject(asunto);
	            helper.setText(contenido, true); // 'true' para permitir HTML
	            mailSender.send(mensaje);
	        } catch (MessagingException e) {
	            throw new RuntimeException("Error al enviar el correo electrónico", e);
	        }
	    }

}
