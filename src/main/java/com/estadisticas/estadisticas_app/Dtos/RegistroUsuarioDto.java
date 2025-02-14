package com.estadisticas.estadisticas_app.Dtos;

public class RegistroUsuarioDto {
	
	private Long id;
	private String nombreUsuario;
    private String emailUsuario;
    private String telefonoUsuario;
    private String passwordUsuario;
   
   
    
    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}


    public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}
	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}
	public String getPasswordUsuario() {
		return passwordUsuario;
	}
	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}
	
	
    
}
