package com.estadisticas.estadisticas_app.Dtos;

/**
 * Clase DTO (Data Transfer Object) que representa los datos de login de un usuario.
 * Esta clase es utilizada para transferir los datos necesarios para la autenticación
 * de un usuario, como su ID, email, contraseña y rol.
 */
public class LoginUsuarioDto {

   
    /** Email del usuario para la autenticación. */
    private String email;
    
    /** Contraseña para autenticar al usuario. */
    private String password;
    
   
    // ============================
    // Getters y Setters
    // ============================

   
    /**
     * Obtiene el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     *
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
}
