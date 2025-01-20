package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", schema = "gestion")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false)
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "email_usuario", nullable = false, unique = true)
    private String emailUsuario;

    @Column(name = "telefono_usuario", nullable = true)
    private String telefonoUsuario;

    @Column(name = "rol_usuario", nullable = false)
    private String rolUsuario;

    @Column(name = "foto_usuario", columnDefinition = "bytea", nullable = true)
    private byte[] fotoUsuario;

    @Column(name = "password_usuario", nullable = false)
    private String passwordUsuario;

    @Column(name = "verificado")
    private boolean verificado = false;

    @Column(name = "verificacion_token")
    private String verificacionToken;

    @Column(name = "token_expiracion")
    private LocalDateTime tokenExpiracion;

    @Column(name = "tipo_suscripcion")
    private String tipoSuscripcion = "FREE"; 

    @Column(name = "fecha_inicio_suscripcion")
    private LocalDateTime fechaInicioSuscripcion;

    @Column(name = "fecha_fin_suscripcion")
    private LocalDateTime fechaFinSuscripcion;

    @Column(name = "estado_suscripcion")
    private String estadoSuscripcion = "INACTIVA";

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Consulta> consultas;


    // Constructores
    public Usuario() {}

    

    public Usuario(Long idUsuario, String nombreUsuario, String emailUsuario, String telefonoUsuario, String rolUsuario,
			byte[] fotoUsuario, String passwordUsuario, boolean verificado, String verificacionToken,
			LocalDateTime tokenExpiracion, String tipoSuscripcion, LocalDateTime fechaInicioSuscripcion,
			LocalDateTime fechaFinSuscripcion, String estadoSuscripcion, List<Consulta> consultas) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.telefonoUsuario = telefonoUsuario;
		this.rolUsuario = rolUsuario;
		this.fotoUsuario = fotoUsuario;
		this.passwordUsuario = passwordUsuario;
		this.verificado = verificado;
		this.verificacionToken = verificacionToken;
		this.tokenExpiracion = tokenExpiracion;
		this.tipoSuscripcion = tipoSuscripcion;
		this.fechaInicioSuscripcion = fechaInicioSuscripcion;
		this.fechaFinSuscripcion = fechaFinSuscripcion;
		this.estadoSuscripcion = estadoSuscripcion;
		this.consultas = consultas;
	}

    

	public Long getIdUsuario() {
		return idUsuario;
	}



	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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



	public String getVerificacionToken() {
		return verificacionToken;
	}



	public void setVerificacionToken(String verificacionToken) {
		this.verificacionToken = verificacionToken;
	}



	public LocalDateTime getTokenExpiracion() {
		return tokenExpiracion;
	}



	public void setTokenExpiracion(LocalDateTime tokenExpiracion) {
		this.tokenExpiracion = tokenExpiracion;
	}



	public String getTipoSuscripcion() {
		return tipoSuscripcion;
	}



	public void setTipoSuscripcion(String tipoSuscripcion) {
		this.tipoSuscripcion = tipoSuscripcion;
	}



	public LocalDateTime getFechaInicioSuscripcion() {
		return fechaInicioSuscripcion;
	}



	public void setFechaInicioSuscripcion(LocalDateTime fechaInicioSuscripcion) {
		this.fechaInicioSuscripcion = fechaInicioSuscripcion;
	}



	public LocalDateTime getFechaFinSuscripcion() {
		return fechaFinSuscripcion;
	}



	public void setFechaFinSuscripcion(LocalDateTime fechaFinSuscripcion) {
		this.fechaFinSuscripcion = fechaFinSuscripcion;
	}



	public String getEstadoSuscripcion() {
		return estadoSuscripcion;
	}



	public void setEstadoSuscripcion(String estadoSuscripcion) {
		this.estadoSuscripcion = estadoSuscripcion;
	}



	public List<Consulta> getConsultas() {
		return consultas;
	}



	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}



	@Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", emailUsuario=" + emailUsuario
                + ", telefonoUsuario=" + telefonoUsuario + ", rolUsuario=" + rolUsuario + "]";
    }
}
