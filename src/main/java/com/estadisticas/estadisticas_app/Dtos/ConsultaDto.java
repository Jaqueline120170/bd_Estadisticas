package com.estadisticas.estadisticas_app.Dtos;


import java.util.Map;

/**
 * DTO utilizado para representar una consulta realizada por un usuario
 * sobre un dataset con filtros específicos.
 */
public class ConsultaDto {

    /**
     * Identificador del usuario que realiza la consulta.
     */
    public Long idUsuario;

    /**
     * Identificador del dataset sobre el que se realiza la consulta.
     */
    public Long idDataset;

    /**
     * Mapa de filtros aplicados en la consulta.
     * La clave es el nombre del campo y el valor es el criterio de filtro.
     */
    public Map<String, Object> filtros;

    /**
     * Obtiene el identificador del usuario que realiza la consulta.
     *
     * @return id del usuario.
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Obtiene el identificador del dataset sobre el que se realiza la consulta.
     *
     * @return id del dataset.
     */
    public Long getIdDataset() {
        return idDataset;
    }

    /**
     * Obtiene los filtros aplicados en la consulta.
     *
     * @return mapa con los filtros.
     */
    public Object getFiltros() {
        return filtros;
    }
}