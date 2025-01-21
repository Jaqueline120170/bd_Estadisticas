package com.estadisticas.estadisticas_app.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByNombreUsuario(String nombreUsuario); // Buscar por nombre de usuario
    List<Usuario> findByRolUsuario(String rolUsuario); // Buscar usuarios por rol
    /**
     * Busca un usuario por su email.
     * 
     * @param emailUsuario el email del usuario.
     * @return el usuario encontrado con ese email.
     */
    Usuario findByEmailUsuario(String emailUsuario);

    /**
     * Verifica si un usuario con el email dado ya existe en la base de datos.
     * 
     * @param emailUsuario el email del usuario.
     * @return true si el usuario con ese email existe, false en caso contrario.
     */
    boolean existsByEmailUsuario(String emailUsuario);

}


