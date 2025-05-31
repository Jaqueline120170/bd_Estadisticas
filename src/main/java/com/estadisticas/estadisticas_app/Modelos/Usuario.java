package com.estadisticas.estadisticas_app.Modelos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Representa un usuario del sistema.
 * Puede ser un administrador o un usuario común, con capacidades de
 * autenticación, descarga y consulta de datasets.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "usuarios", schema = "gestion")
public class Usuario {

    /** Identificador único del usuario (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false)
    private Long idUsuario;

    /** Nombre del usuario. */
    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    /** Email del usuario (único). */
    @Column(name = "email_usuario", nullable = false, unique = true)
    private String emailUsuario;

    /** Número de teléfono del usuario. */
    @Column(name = "telefono_usuario", nullable = true)
    private String telefonoUsuario;

    /** Rol asignado al usuario (e.g., ADMIN o USER). */
    @Column(name = "rol_usuario")
    private String rolUsuario;

    /** Fotografía del usuario almacenada en formato binario. */
    @Column(name = "foto_usuario", columnDefinition = "bytea", nullable = true)
    private byte[] fotoUsuario;

    /** Contraseña encriptada del usuario. */
    @Column(name = "password_usuario", nullable = false)
    private String passwordUsuario;

    /** Indica si el usuario ha verificado su cuenta. */
    @Column(name = "verificado")
    private boolean verificado;

    /** Token de verificación para activación de cuenta. */
    @Column(name = "verificacion_token")
    private String verificacionToken;

    /** Fecha de expiración del token de verificación. */
    @Column(name = "token_expiracion")
    private LocalDateTime tokenExpiracion;

    /** Token para restablecimiento de contraseña. */
    @Column(name = "reset_token")
    private String resetToken;

    /** Fecha de expiración del token de restablecimiento. */
    @Column(name = "reset_token_expiracion")
    private LocalDateTime resetTokenExpiracion;

    /** Consultas realizadas por el usuario. */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas;

    /** Descargas realizadas por el usuario. */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Descarga> descargas;

    /** Constructor vacío requerido por JPA. */
    public Usuario() {}

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", telefonoUsuario='" + telefonoUsuario + '\'' +
                ", rolUsuario='" + rolUsuario + '\'' +
                ", verificado=" + verificado +
                ", verificacionToken='" + verificacionToken + '\'' +
                ", tokenExpiracion=" + tokenExpiracion +
                ", resetToken='" + resetToken + '\'' +
                ", resetTokenExpiracion=" + resetTokenExpiracion +
                '}';
    }

    /** @return ID del usuario. */
    public Long getIdUsuario() { return idUsuario; }

    /** @param idUsuario ID a establecer. */
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    /** @return Nombre del usuario. */
    public String getNombreUsuario() { return nombreUsuario; }

    /** @param nombreUsuario Nombre a establecer. */
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    /** @return Email del usuario. */
    public String getEmailUsuario() { return emailUsuario; }

    /** @param emailUsuario Email a establecer. */
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }

    /** @return Teléfono del usuario. */
    public String getTelefonoUsuario() { return telefonoUsuario; }

    /** @param telefonoUsuario Teléfono a establecer. */
    public void setTelefonoUsuario(String telefonoUsuario) { this.telefonoUsuario = telefonoUsuario; }

    /** @return Rol del usuario. */
    public String getRolUsuario() { return rolUsuario; }

    /** @param rolUsuario Rol a establecer. */
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }

    /** @return Foto del usuario en formato byte[]. */
    public byte[] getFotoUsuario() { return fotoUsuario; }

    /** @param fotoUsuario Foto en byte[] a establecer. */
    public void setFotoUsuario(byte[] fotoUsuario) { this.fotoUsuario = fotoUsuario; }

    /** @return Contraseña del usuario. */
    public String getPasswordUsuario() { return passwordUsuario; }

    /** @param passwordUsuario Contraseña a establecer. */
    public void setPasswordUsuario(String passwordUsuario) { this.passwordUsuario = passwordUsuario; }

    /** @return true si el usuario está verificado. */
    public boolean isVerificado() { return verificado; }

    /** @param verificado Estado de verificación a establecer. */
    public void setVerificado(boolean verificado) { this.verificado = verificado; }

    /** @return Token de verificación. */
    public String getVerificacionToken() { return verificacionToken; }

    /** @param verificacionToken Token de verificación a establecer. */
    public void setVerificacionToken(String verificacionToken) { this.verificacionToken = verificacionToken; }

    /** @return Fecha de expiración del token de verificación. */
    public LocalDateTime getTokenExpiracion() { return tokenExpiracion; }

    /** @param tokenExpiracion Fecha de expiración a establecer. */
    public void setTokenExpiracion(LocalDateTime tokenExpiracion) { this.tokenExpiracion = tokenExpiracion; }

    /** @return Token de restablecimiento de contraseña. */
    public String getResetToken() { return resetToken; }

    /** @param resetToken Token de restablecimiento a establecer. */
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    /** @return Fecha de expiración del token de restablecimiento. */
    public LocalDateTime getResetTokenExpiracion() { return resetTokenExpiracion; }

    /** @param resetTokenExpiracion Fecha de expiración a establecer. */
    public void setResetTokenExpiracion(LocalDateTime resetTokenExpiracion) {
        this.resetTokenExpiracion = resetTokenExpiracion;
    }

    /** @return Lista de consultas realizadas por el usuario. */
    public List<Consulta> getConsultas() { return consultas; }

    /** @param consultas Lista de consultas a establecer. */
    public void setConsultas(List<Consulta> consultas) { this.consultas = consultas; }

    /** @return Lista de descargas realizadas por el usuario. */
    public List<Descarga> getDescargas() { return descargas; }

    /** @param descargas Lista de descargas a establecer. */
    public void setDescargas(List<Descarga> descargas) { this.descargas = descargas; }
}