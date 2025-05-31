package com.estadisticas.estadisticas_app.Seguridad;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    /**
     * Método que configura un bean de tipo PasswordEncoder.
     * <p>
     * El PasswordEncoder se utiliza para codificar contraseñas de forma segura antes de almacenarlas en la base de datos.
     * En este caso, se utiliza BCryptPasswordEncoder, que es un algoritmo de hash seguro para contraseñas, 
     * el cual también incluye un "salting" para proteger contra ataques de diccionario y rainbow tables.
     * </p>
     * @return un PasswordEncoder configurado con el algoritmo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Se retorna una instancia de BCryptPasswordEncoder que proporcionará un hashing seguro de contraseñas
        return new BCryptPasswordEncoder();
    }
  
}