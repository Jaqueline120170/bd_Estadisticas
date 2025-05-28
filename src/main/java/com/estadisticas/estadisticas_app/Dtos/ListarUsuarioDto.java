package com.estadisticas.estadisticas_app.Dtos;

public class ListarUsuarioDto {
	  private Long idUsuario;
	    private String nombreUsuario;
	    private String emailUsuario;
	    private String rolUsuario;
	    
	    
	    
	    public ListarUsuarioDto() {
			super();
		}

		public ListarUsuarioDto(Long idUsuario, String nombreUsuario, String emailUsuario, String rolUsuario) {
			super();
			this.idUsuario = idUsuario;
			this.nombreUsuario = nombreUsuario;
			this.emailUsuario = emailUsuario;
			this.rolUsuario = rolUsuario;
		}

		// Getters y Setters
	    public Long getIdUsuario() {
	        return idUsuario;
	    }

	    public void setIdUsuario(Long idUsuario) {
	        this.idUsuario = idUsuario;
	    }

	    public String getNombreUsuario() {
	        return nombreUsuario;
	    }

	    public void setNombreUsuario(String nombreUsuario) {
	        this.nombreUsuario = nombreUsuario;
	    }

	    public String getEmailUsuario() {
	        return emailUsuario;
	    }

	    public void setEmailUsuario(String emailUsuario) {
	        this.emailUsuario = emailUsuario;
	    }

	    public String getRolUsuario() {
	        return rolUsuario;
	    }

	    public void setRolUsuario(String rolUsuario) {
	        this.rolUsuario = rolUsuario;
	    }


}
