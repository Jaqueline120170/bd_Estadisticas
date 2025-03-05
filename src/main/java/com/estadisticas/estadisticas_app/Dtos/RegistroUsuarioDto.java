package com.estadisticas.estadisticas_app.Dtos;

/**
 * DTO (Data Transfer Object) que representa los datos necesarios para registrar un nuevo usuario.
 * Contiene los detalles personales y de contacto del usuario, así como la contraseña para el registro.
 * Se utiliza en el proceso de registro de un usuario en la plataforma.
 */
public class RegistroUsuarioDto {
	
    /** Identificador único del usuario. */
	private Long id;
	
    /** Nombre del usuario. */
    private String nombreUsuario;
    
    /** Correo electrónico del usuario. */
    private String emailUsuario;
    
    /** Teléfono de contacto del usuario. */
    private String telefonoUsuario;
    
    /** Contraseña del usuario. */
    private String passwordUsuario;

    /**
     * Obtiene el ID del usuario.
     *
     * @return el ID del usuario.
     */
    public Long getId() {
		return id;
	}

    /**
     * Establece el ID del usuario.
     *
     * @param id el ID del usuario.
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario.
     */
    public String getNombreUsuario() {
		return nombreUsuario;
	}
    
    /**
     * Establece el nombre del usuario.
     *
     * @param nombreUsuario el nombre del usuario.
     */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
    
    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return el correo electrónico del usuario.
     */
	public String getEmailUsuario() {
		return emailUsuario;
	}
    
    /**
     * Establece el correo electrónico del usuario.
     *
     * @param emailUsuario el correo electrónico del usuario.
     */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
    
    /**
     * Obtiene el teléfono de contacto del usuario.
     *
     * @return el teléfono de contacto del usuario.
     */
	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}
    
    /**
     * Establece el teléfono de contacto del usuario.
     *
     * @param telefonoUsuario el teléfono de contacto del usuario.
     */
	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}
    
    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario.
     */
	public String getPasswordUsuario() {
		return passwordUsuario;
	}
    
    /**
     * Establece la contraseña del usuario.
     *
     * @param passwordUsuario la contraseña del usuario.
     */
	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}
}
