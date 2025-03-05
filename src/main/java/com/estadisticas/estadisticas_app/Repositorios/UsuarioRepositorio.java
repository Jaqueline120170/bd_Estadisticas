package com.estadisticas.estadisticas_app.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estadisticas.estadisticas_app.Modelos.Usuario;

/**
 * Repositorio para la entidad `Usuario`.
 * Extiende de `JpaRepository` para proporcionar acceso a las operaciones CRUD básicas sobre la tabla "usuarios".
 * Además, contiene métodos personalizados para consultas específicas relacionadas con los usuarios.
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param nombreUsuario el nombre del usuario.
     * @return un `Optional` que contiene el usuario encontrado con ese nombre.
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca usuarios por rol.
     * 
     * @param rolUsuario el rol de los usuarios (por ejemplo, "admin", "usuario", "premium").
     * @return una lista de usuarios con ese rol.
     */
    List<Usuario> findByRolUsuario(String rolUsuario);

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param emailUsuario el correo electrónico del usuario.
     * @return un `Optional` que contiene el usuario encontrado con ese correo.
     */
    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    /**
     * Verifica si un usuario con el correo electrónico dado ya existe en la base de datos.
     * 
     * @param emailUsuario el correo electrónico del usuario.
     * @return `true` si el usuario con ese correo electrónico ya existe, `false` en caso contrario.
     */
    boolean existsByEmailUsuario(String emailUsuario);

    /**
     * Busca todos los usuarios con un rol específico.
     * 
     * @param rolUsuario el rol de los usuarios (por ejemplo, "admin", "usuario").
     * @return una lista de usuarios con ese rol.
     */
    List<Usuario> findByRolUsuarioIgnoreCase(String rolUsuario);

    /**
     * Busca un usuario por su token de verificación.
     * 
     * @param verificacionToken el token de verificación del usuario.
     * @return el usuario que corresponde a ese token de verificación.
     */
    Usuario findByVerificacionToken(String verificacionToken);

    /**
     * Busca un usuario por su token de reseteo de contraseña.
     * 
     * @param resetToken el token para resetear la contraseña del usuario.
     * @return un `Optional` que contiene el usuario que corresponde a ese token.
     */
    Optional<Usuario> findByResetToken(String resetToken); 

    // Métodos para contar usuarios según ciertos criterios

    /**
     * Cuenta cuántos usuarios están verificados.
     * 
     * @param verificado el estado de verificación (true para verificados, false para no verificados).
     * @return la cantidad de usuarios con ese estado de verificación.
     */
    long countByVerificado(boolean verificado);

    /**
     * Cuenta cuántos usuarios existen con un rol específico.
     * 
     * @param rolUsuario el rol de los usuarios (por ejemplo, "ADMIN" o "USUARIO").
     * @return la cantidad de usuarios con ese rol.
     */
    long countByRolUsuario(String rolUsuario);

    /**
     * Cuenta cuántos usuarios existen con un tipo de suscripción específico.
     * 
     * @param tipoSuscripcion el tipo de suscripción (por ejemplo, "FREE" o "PREMIUM").
     * @return la cantidad de usuarios con ese tipo de suscripción.
     */
    long countByTipoSuscripcion(String tipoSuscripcion);

    /**
     * Cuenta cuántos usuarios existen con un estado de suscripción específico.
     * 
     * @param estadoSuscripcion el estado de la suscripción (por ejemplo, "ACTIVA" o "INACTIVA").
     * @return la cantidad de usuarios con ese estado de suscripción.
     */
    long countByEstadoSuscripcion(String estadoSuscripcion);

    /**
     * Busca un usuario por su ID único.
     * 
     * @param id el ID único del usuario.
     * @return un `Optional` que contiene el usuario encontrado con ese ID.
     */
    Optional<Usuario> findById(Long id);
}


