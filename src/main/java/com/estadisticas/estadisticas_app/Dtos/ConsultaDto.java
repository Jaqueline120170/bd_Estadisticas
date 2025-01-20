package com.estadisticas.estadisticas_app.Dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultaDto {
    
    private Long idConsulta;
    private Long idUsuario; // Refleja el usuario que realizó la consulta
    private List<Long> idIndicadores; // Lista de identificadores de los indicadores consultados
    private String parametros; // Parámetros utilizados (ej. rango de fechas)
    private LocalDateTime fechaConsulta; // Fecha y hora de la consulta
    
    // Getters y Setters
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Long> getIdIndicadores() {
        return idIndicadores;
    }

    public void setIdIndicadores(List<Long> idIndicadores) {
        this.idIndicadores = idIndicadores;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }
}
