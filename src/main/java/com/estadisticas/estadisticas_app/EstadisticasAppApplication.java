package com.estadisticas.estadisticas_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import jakarta.annotation.PostConstruct;

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
        // Arranca la aplicación Spring Boot
        SpringApplication.run(EstadisticasAppApplication.class, args);
        
    }
    @PostConstruct
    public void logDatabaseEnv() {
        System.out.println("=== DATABASE CONFIG ===");
        System.out.println("URL: " + System.getenv("DATABASE_PUBLIC_URL"));
        System.out.println("USER: " + System.getenv("PGUSER"));
        System.out.println("PASS: " + System.getenv("PGPASSWORD"));
    }
    
}
