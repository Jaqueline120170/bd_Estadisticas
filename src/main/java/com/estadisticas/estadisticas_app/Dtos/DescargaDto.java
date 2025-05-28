package com.estadisticas.estadisticas_app.Dtos;



public class DescargaDto {

    private Long id;
    public String formato; // CSV, JSON, Excel
    public Long idUsuario;
    public Long idDataset;
    public Long idConsulta; 

    public Long getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}

	public DescargaDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdDataset() {
        return idDataset;
    }

    public void setIdDataset(Long idDataset) {
        this.idDataset = idDataset;
    }

	
}
