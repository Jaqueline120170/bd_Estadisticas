
package com.estadisticas.estadisticas_app.Dtos;

/**
 * Clase DTO (Data Transfer Object) que representa los datos de login de un usuario.
 * Esta clase es utilizada para transferir los datos necesarios para la autenticación
 * de un usuario, como su ID, email, contraseña y rol.
 */
public class LoginUsuarioDto {

   
    /** Email del usuario para la autenticación. */
    private String emailUsuario;
    
    /** Contraseña para autenticar al usuario. */
    private String passwordUsuario;
    
    // ============================
    // Getters y Setters
    // ============================

   
    /**
     * Obtiene el email del usuario.
     *
     * @return El email del usuario.
     */
    
	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getPasswordUsuario() {
		return passwordUsuario;
	}

	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}
    
   
   
    
}
