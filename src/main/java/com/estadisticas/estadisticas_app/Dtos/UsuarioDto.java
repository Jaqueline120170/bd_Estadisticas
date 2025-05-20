package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDateTime;
import java.util.Base64;

import com.estadisticas.estadisticas_app.Modelos.Usuario;

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
    private String fotoUsuario;

    /** Contraseña del usuario. */
    private String passwordUsuario;

    /** Estado de verificación del usuario (si ha sido verificado o no). */
    private boolean verificado;

    
    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getIdUsuario();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.emailUsuario = usuario.getEmailUsuario();
        this.rolUsuario = usuario.getRolUsuario();
        this.telefonoUsuario = usuario.getTelefonoUsuario();
        this.verificado = usuario.isVerificado();

        if (usuario.getFotoUsuario() != null) {
            this.fotoUsuario = Base64.getEncoder().encodeToString(usuario.getFotoUsuario());
        } else {
            this.fotoUsuario = null;
        }
    }


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
    public String getFotoUsuario() {
        return fotoUsuario;
    }

    /**
     * Establece la foto del usuario en formato de array de bytes.
     *
     * @param fotoUsuario la foto del usuario.
     */

    public void setFotoUsuario(String fotoUsuario) {
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

    

   
}
