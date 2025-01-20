package com.estadisticas.estadisticas_app.Modelos;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", schema = "gestion")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "email_usuario", nullable = false, unique = true)//LOS EMAILS NO SE PUEDEN REPETIR
    private String emailUsuario;

    @Column(name = "telefono_usuario", nullable = true)
    private String telefonoUsuario;

    @Column(name = "rol_usuario", nullable = false)
    private String rolUsuario;

    @Lob
    @Column(name = "foto_usuario", columnDefinition = "bytea", nullable = true)
    private byte[] foto;

    @Column(name = "password_usuario", nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Consulta> consultas;

    // Constructores
    public Usuario() {}

    public Usuario(Long id, String nombreUsuario, String emailUsuario, String telefonoUsuario, String rolUsuario,
                   byte[] foto, String password) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.rolUsuario = rolUsuario;
        this.foto = foto;
        this.password = password;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", emailUsuario=" + emailUsuario
                + ", telefonoUsuario=" + telefonoUsuario + ", rolUsuario=" + rolUsuario + "]";
    }
}
