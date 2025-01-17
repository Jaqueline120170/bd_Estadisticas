package com.estadisticas.estadisticas_app.Modelos;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios", schema = "gestion")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", updatable = false)
    private Long id;
	
	@Column(name = "nombre_usuario")
    private String nombreUsuario;
	
	@Column(name = "email_usuario")
    private String emailUsuario;
	
	@Column(name = "telefono_usuario")
    private String telefonoUsuario;
	
	@Column(name = "rol_usuario")
    private String rolUsuario;
	
	@Column(name = "foto_usuario", columnDefinition = "bytea")
    private byte[] foto;
	
    @Column(name= "password_Usuario",nullable = false)
    private String password;

	
    public Usuario() {
		super();
	}

	public Usuario(Long id, String nombreUsuario, String emailUsuario, String telefonoUsuario, String rolUsuario,
			byte[] foto, String password) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.telefonoUsuario = telefonoUsuario;
		this.rolUsuario = rolUsuario;
		this.foto = foto;
		this.password = password;
	}

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

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", emailUsuario=" + emailUsuario
				+ ", telefonoUsuario=" + telefonoUsuario + ", rolUsuario=" + rolUsuario + ", foto="
				+ Arrays.toString(foto) + ", password=" + password + "]";
	}
    
    
}
