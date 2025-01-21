package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDateTime;

public class UsuarioDto {

    private Long id;
    private String nombreUsuario;
    private String emailUsuario;
    private String rolUsuario;
    private String telefonoUsuario;
    private byte [] fotoUsuario; // Cambiado a String si solo almacenamos la URL
    private String passwordUsuario;
    private boolean verificado;
    private LocalDateTime fechaInicioSuscripcion;
    private EstadoSuscripcion estadoSuscripcion; // Usamos un Enum para el estado de suscripción
    private LocalDateTime fechaFinSuscripcion;
    
    

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



	public String getRolUsuario() {
		return rolUsuario;
	}



	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}



	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}



	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}



	public byte[] getFotoUsuario() {
		return fotoUsuario;
	}



	public void setFotoUsuario(byte[] fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}



	public String getPasswordUsuario() {
		return passwordUsuario;
	}



	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}



	public boolean isVerificado() {
		return verificado;
	}



	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}



	public LocalDateTime getFechaInicioSuscripcion() {
		return fechaInicioSuscripcion;
	}



	public void setFechaInicioSuscripcion(LocalDateTime fechaInicioSuscripcion) {
		this.fechaInicioSuscripcion = fechaInicioSuscripcion;
	}



	public EstadoSuscripcion getEstadoSuscripcion() {
		return estadoSuscripcion;
	}



	public void setEstadoSuscripcion(EstadoSuscripcion estadoSuscripcion) {
		this.estadoSuscripcion = estadoSuscripcion;
	}



	public LocalDateTime getFechaFinSuscripcion() {
		return fechaFinSuscripcion;
	}



	public void setFechaFinSuscripcion(LocalDateTime fechaFinSuscripcion) {
		this.fechaFinSuscripcion = fechaFinSuscripcion;
	}



	// Enum para manejar los estados de suscripción
    public enum EstadoSuscripcion {
        ACTIVA,
        CADUCADA,
        PENDIENTE
    }
}
