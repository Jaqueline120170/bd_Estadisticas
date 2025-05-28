package com.estadisticas.estadisticas_app.Dtos;

import com.estadisticas.estadisticas_app.Modelos.Categoria;

public class CategoriaDto {
	
	private Long idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;

    public CategoriaDto(Categoria categoria) {
        this.idCategoria = categoria.getIdCategoria();
        this.nombreCategoria = categoria.getNombreCategoria();
        this.descripcionCategoria = categoria.getDescripcionCategoria();
    }

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}

	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}
    
    

}
