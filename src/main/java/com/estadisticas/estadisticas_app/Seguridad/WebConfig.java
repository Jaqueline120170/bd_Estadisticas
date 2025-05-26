package com.estadisticas.estadisticas_app.Seguridad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para la aplicación.
 * Esta clase implementa la interfaz `WebMvcConfigurer` y configura el CORS (Cross-Origin Resource Sharing) 
 * para permitir que el frontend de la aplicación, alojado en `http://localhost:4200`, pueda hacer solicitudes
 * a los servicios del backend sin problemas de CORS.
 * 
 * CORS es una política de seguridad implementada por los navegadores para restringir las solicitudes de recursos
 * a dominios diferentes del que sirve la página web. Esta clase permite establecer excepciones a esa política.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura los mapeos de CORS para la aplicación.
     * Permite solicitudes desde `http://localhost:4200` (usualmente para un frontend en Angular) 
     * y establece los métodos y encabezados permitidos.
     *
     * @param registry el registro de CORS, que se utiliza para definir las reglas de CORS.
     */
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	            .allowedOrigins("*")  // Permitir cualquier origen (solo para pruebas)
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	            .allowedHeaders("*")
	            .allowCredentials(false);  // No permitir cookies / credenciales
	    }

	}