package com.estadisticas.estadisticas_app.Dtos;

/**
 * DTO que representa la información necesaria para realizar una descarga.
 */
public class DescargaDto {

    /**
     * Identificador único de la descarga.
     */
    private Long id;

    /**
     * Formato de la descarga, por ejemplo: CSV, JSON, Excel.
     */
    public String formato;

    /**
     * Identificador del usuario que realiza la descarga.
     */
    public Long idUsuario;

    /**
     * Identificador del dataset asociado a la descarga.
     */
    public Long idDataset;

    /**
     * Identificador de la consulta asociada a la descarga.
     */
    public Long idConsulta;

    /**
     * Constructor vacío por defecto.
     */
    public DescargaDto() {}

    /**
     * Obtiene el identificador de la descarga.
     *
     * @return id de la descarga.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la descarga.
     *
     * @param id id de la descarga.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el formato de la descarga.
     *
     * @return formato de la descarga.
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Establece el formato de la descarga.
     *
     * @param formato formato de la descarga.
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * Obtiene el identificador del usuario que realiza la descarga.
     *
     * @return id del usuario.
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario que realiza la descarga.
     *
     * @param idUsuario id del usuario.
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el identificador del dataset asociado a la descarga.
     *
     * @return id del dataset.
     */
    public Long getIdDataset() {
        return idDataset;
    }

    /**
     * Establece el identificador del dataset asociado a la descarga.
     *
     * @param idDataset id del dataset.
     */
    public void setIdDataset(Long idDataset) {
        this.idDataset = idDataset;
    }

    /**
     * Obtiene el identificador de la consulta asociada a la descarga.
     *
     * @return id de la consulta.
     */
    public Long getIdConsulta() {
        return idConsulta;
    }

    /**
     * Establece el identificador de la consulta asociada a la descarga.
     *
     * @param idConsulta id de la consulta.
     */
    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }
}