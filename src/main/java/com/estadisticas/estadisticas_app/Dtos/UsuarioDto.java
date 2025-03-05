package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) que representa los datos de un usuario en el sistema.
 * Incluye detalles personales, información de suscripción, estado de verificación y datos de contacto.
 */
public class UsuarioDto {

    /** Identificador único del usuario. */
    private Long id;

    /** Nombre del usuario. */
    private String nombreUsuario;

    /** Correo electrónico del usuario. */
    private String emailUsuario;

    /** Rol del usuario (por ejemplo, "ADMIN", "USUARIO"). */
    private String rolUsuario;

    /** Teléfono de contacto del usuario. */
    private String telefonoUsuario;

    /** Foto del usuario en forma de array de bytes (puede cambiar a String si solo almacenamos la URL de la foto). */
    private byte[] fotoUsuario;

    /** Contraseña del usuario. */
    private String passwordUsuario;

    /** Estado de verificación del usuario (si ha sido verificado o no). */
    private boolean verificado;

    /** Fecha de inicio de la suscripción del usuario. */
    private LocalDateTime fechaInicioSuscripcion;

    /** Estado de la suscripción del usuario (activa, caducada o pendiente). */
    private EstadoSuscripcion estadoSuscripcion;

    /** Fecha de finalización de la suscripción del usuario. */
    private LocalDateTime fechaFinSuscripcion;

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
     * Obtiene el rol del usuario.
     *
     * @return el rol del usuario.
     */
    public String getRolUsuario() {
        return rolUsuario;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rolUsuario el rol del usuario.
     */
    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    /**
     * Obtiene el teléfono de contacto del usuario.
     *
     * @return el teléfono del usuario.
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
     * Obtiene la foto del usuario en formato de array de bytes.
     *
     * @return la foto del usuario.
     */
    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }

    /**
     * Establece la foto del usuario en formato de array de bytes.
     *
     * @param fotoUsuario la foto del usuario.
     */
    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
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

    /**
     * Obtiene el estado de verificación del usuario.
     *
     * @return true si el usuario está verificado, false en caso contrario.
     */
    public boolean isVerificado() {
        return verificado;
    }

    /**
     * Establece el estado de verificación del usuario.
     *
     * @param verificado estado de verificación del usuario.
     */
    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    /**
     * Obtiene la fecha de inicio de la suscripción del usuario.
     *
     * @return la fecha de inicio de la suscripción.
     */
    public LocalDateTime getFechaInicioSuscripcion() {
        return fechaInicioSuscripcion;
    }

    /**
     * Establece la fecha de inicio de la suscripción del usuario.
     *
     * @param fechaInicioSuscripcion la fecha de inicio de la suscripción.
     */
    public void setFechaInicioSuscripcion(LocalDateTime fechaInicioSuscripcion) {
        this.fechaInicioSuscripcion = fechaInicioSuscripcion;
    }

    /**
     * Obtiene el estado de la suscripción del usuario.
     *
     * @return el estado de la suscripción (ACTIVA, CADUCADA, PENDIENTE).
     */
    public EstadoSuscripcion getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    /**
     * Establece el estado de la suscripción del usuario.
     *
     * @param estadoSuscripcion el estado de la suscripción.
     */
    public void setEstadoSuscripcion(EstadoSuscripcion estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
    }

    /**
     * Obtiene la fecha de finalización de la suscripción del usuario.
     *
     * @return la fecha de finalización de la suscripción.
     */
    public LocalDateTime getFechaFinSuscripcion() {
        return fechaFinSuscripcion;
    }

    /**
     * Establece la fecha de finalización de la suscripción del usuario.
     *
     * @param fechaFinSuscripcion la fecha de finalización de la suscripción.
     */
    public void setFechaFinSuscripcion(LocalDateTime fechaFinSuscripcion) {
        this.fechaFinSuscripcion = fechaFinSuscripcion;
    }

    /**
     * Enum que representa los posibles estados de la suscripción del usuario.
     */
    public enum EstadoSuscripcion {
        ACTIVA,    // Suscripción activa
        CADUCADA,  // Suscripción caducada
        PENDIENTE  // Suscripción pendiente
    }
}
