package com.estadisticas.estadisticas_app;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Clase principal de la aplicación que arranca el servidor Spring Boot.
 * Excluye la configuración de seguridad predeterminada de Spring Security.
 * Esta clase es el punto de entrada de la aplicación.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EstadisticasAppApplication {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos
     */
	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(EstadisticasAppApplication.class);
	    String port = System.getenv("PORT");
	    if (port != null) {
	        app.setDefaultProperties(Collections.singletonMap("server.port", port));
	    }
	    app.run(args);
	}

}
