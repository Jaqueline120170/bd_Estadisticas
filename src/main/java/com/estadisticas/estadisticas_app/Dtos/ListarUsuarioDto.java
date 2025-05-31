package com.estadisticas.estadisticas_app.Dtos;

/**
 * DTO para listar información básica de un usuario.
 */
public class ListarUsuarioDto {

    /**
     * Identificador único del usuario.
     */
    private Long idUsuario;

    /**
     * Nombre del usuario.
     */
    private String nombreUsuario;

    /**
     * Correo electrónico del usuario.
     */
    private String emailUsuario;

    /**
     * Rol asignado al usuario.
     */
    private String rolUsuario;

    /**
     * Constructor vacío por defecto.
     */
    public ListarUsuarioDto() {
        super();
    }

    /**
     * Constructor que inicializa todas las propiedades del DTO.
     *
     * @param idUsuario     Identificador único del usuario.
     * @param nombreUsuario Nombre del usuario.
     * @param emailUsuario  Correo electrónico del usuario.
     * @param rolUsuario    Rol asignado al usuario.
     */
    public ListarUsuarioDto(Long idUsuario, String nombreUsuario, String emailUsuario, String rolUsuario) {
        super();
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.rolUsuario = rolUsuario;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return id del usuario.
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param idUsuario id del usuario.
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombreUsuario nombre del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return email del usuario.
     */
    public String getEmailUsuario() {
        return emailUsuario;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param emailUsuario email del usuario.
     */
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    /**
     * Obtiene el rol asignado al usuario.
     *
     * @return rol del usuario.
     */
    public String getRolUsuario() {
        return rolUsuario;
    }

    /**
     * Establece el rol asignado al usuario.
     *
     * @param rolUsuario rol del usuario.
     */
    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
}
