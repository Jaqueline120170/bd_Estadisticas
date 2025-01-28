package com.estadisticas.estadisticas_app.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param nombreUsuario el nombre del usuario.
     * @return el usuario encontrado con ese nombre.
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca usuarios por rol.
     * 
     * @param rolUsuario el rol de los usuarios ("admin", "usuario", "premium").
     * @return una lista de usuarios con ese rol.
     */
    List<Usuario> findByRolUsuario(String rolUsuario);

    /**
     * Busca un usuario por su email.
     * 
     * @param emailUsuario el email del usuario.
     * @return un Optional con el usuario encontrado.
     */
    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    /**
     * Verifica si un usuario con el email dado ya existe en la base de datos.
     * 
     * @param emailUsuario el email del usuario.
     * @return true si el usuario con ese email existe, false en caso contrario.
     */
    boolean existsByEmailUsuario(String emailUsuario);

    /**
     * Busca todos los usuarios con el rol "premium".
     * 
     * @return una lista de usuarios con el rol "premium".
     */
    List<Usuario> findByRolUsuarioIgnoreCase(String rolUsuario);
    
    Usuario findByVerificacionToken(String verificacionToken);

    Optional<Usuario> findByResetToken(String resetToken); 
  
}


