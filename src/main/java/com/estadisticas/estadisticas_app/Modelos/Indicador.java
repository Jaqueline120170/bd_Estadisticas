package com.estadisticas.estadisticas_app.Modelos;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "indicadores", schema = "gestion")
public class Indicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_indicador", updatable = false)
    private Long idIndicador;

    @Column(name = "nombre_indicador", nullable = false)
    private String nombreIndicador;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    // Relación n:1 con Dataset
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dataset", nullable = false)
    private Dataset dataset;

    // Relación n:m con Consultas
    @ManyToMany(mappedBy = "indicadores", fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    // Constructores
    public Indicador() {}

    public Indicador(Long idIndicador, String nombreIndicador, String unidadMedida, Dataset dataset) {
        this.idIndicador = idIndicador;
        this.nombreIndicador = nombreIndicador;
        this.unidadMedida = unidadMedida;
        this.dataset = dataset;
    }

    // Getters y Setters
    public Long getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Long idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(String nombreIndicador) {
        this.nombreIndicador = nombreIndicador;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return "Indicador [idIndicador=" + idIndicador + ", nombreIndicador=" + nombreIndicador + ", unidadMedida=" + unidadMedida + "]";
    }
}
