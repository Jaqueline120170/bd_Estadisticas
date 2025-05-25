package com.estadisticas.estadisticas_app;

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
    //(public static void main(String[] args) {
        // Arranca la aplicación Spring Boot
       // SpringApplication.run(EstadisticasAppApplication.class, args);
    //}
	 public static void main(String[] args) {
	        String port = System.getenv("PORT");
	        if (port != null) {
	            System.setProperty("server.port", port);
	            System.out.println("Usando puerto de Railway: " + port);
	        } else {
	            System.out.println("No se encontró la variable PORT. Usando puerto por defecto 8080");
	        }

	        SpringApplication.run(EstadisticasAppApplication.class, args);
	    }

}
