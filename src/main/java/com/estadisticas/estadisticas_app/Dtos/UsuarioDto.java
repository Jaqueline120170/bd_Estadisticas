package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDateTime;

public class UsuarioDto {

    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private String telefono;
    private byte [] foto; // Cambiado a String si solo almacenamos la URL
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
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
