package com.estadisticas.estadisticas_app.Servicios;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;


@Service
public class AdministradorServicio {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorServicio.class);
   
    @Autowired
    private UsuarioRepositorio usuarioRepository;
    

    /**
     * Lista todos los usuarios registrados en el sistema.
     * @return Lista de usuarios.
     */
    public List<Usuario> listarTodosLosUsuarios() {
        logger.info("Listando todos los usuarios...");
        return usuarioRepository.findAll();
    }

    /**
     * Modifica los datos de un usuario existente.
     * @param id ID del usuario a modificar.
     * @param usuarioActualizado Datos actualizados del usuario.
     * @param foto Nueva foto de perfil (opcional).
     * @return Usuario actualizado.
     */
    public Usuario modificarUsuario(Long id, Usuario usuarioActualizado, MultipartFile foto) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
                usuario.setTelefonoUsuario(usuarioActualizado.getTelefonoUsuario());
                usuario.setRolUsuario(usuarioActualizado.getRolUsuario());
                usuario.setVerificado(usuarioActualizado.isVerificado());

                if (foto != null && !foto.isEmpty()) {
                    try {
                        usuario.setFotoUsuario(foto.getBytes());
                        logger.info("Foto de perfil actualizada para el usuario ID: {}", id);
                    } catch (IOException e) {
                        logger.error("Error al procesar la imagen para el usuario ID: {}", id, e);
                        throw new RuntimeException("Error al procesar la imagen", e);
                    }
                }

                Usuario actualizado = usuarioRepository.save(usuario);
                logger.info("Usuario con ID {} ha sido modificado.", id);
                return actualizado;
            })
            .orElseThrow(() -> {
                logger.warn("Intento de modificar usuario no encontrado con ID: {}", id);
                return new IllegalArgumentException("Usuario no encontrado con ID: " + id);
            });
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar.
     * Este método lanza IllegalArgumentException si el ID no existe o corresponde al administrador.
     */
    public void eliminarUsuario(Long id) {
    	final Long ID_ADMIN = (long) 20; // Asumes que el admin tiene este ID fijo
        if (!usuarioRepository.existsById(id)) {
            logger.warn("Intento de eliminar usuario no encontrado con ID: {}", id);
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + id);
        }
        if (id.equals(ID_ADMIN)) {
            logger.warn("Intento de eliminar al administrador con ID: {}", id);
            throw new IllegalArgumentException("No se puede eliminar al administrador.");
        }
        usuarioRepository.deleteById(id);
        logger.info("Usuario con ID {} ha sido eliminado.", id);
    }

    /**
     * Obtiene estadísticas sobre los usuarios del sistema.
     * @return Mapa con estadísticas de usuarios.
     */
    public Map<String, Long> obtenerEstadisticasUsuarios() {
        logger.info("Obteniendo estadísticas de usuarios...");
        try {
        long totalUsuarios = usuarioRepository.count();
        long usuariosVerificados = usuarioRepository.countByVerificado(true);
        long usuariosNoVerificados = totalUsuarios - usuariosVerificados;
        long admins = usuarioRepository.countByRolUsuario("ADMIN");
        long usuarios = usuarioRepository.countByRolUsuario("USUARIO");

        
        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("Total de Usuarios", totalUsuarios);
        estadisticas.put("Usuarios Verificados", usuariosVerificados);
        estadisticas.put("Usuarios No Verificados", usuariosNoVerificados);
        estadisticas.put("Administradores", admins);
        estadisticas.put("Usuarios", usuarios);
        logger.info("Estadísticas generadas exitosamente.");
        return estadisticas;
        }
        catch (Exception e) {
            logger.error("Error al obtener estadísticas de usuarios: {}", e.getMessage());
            throw new RuntimeException("Error al obtener estadísticas de usuarios", e);
        }
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado.
     */
    @Transactional
    public Usuario buscarUsuarioPorId(Long id) {
        logger.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Usuario no encontrado con ID: {}", id);
                return new IllegalArgumentException("Usuario no encontrado con ID: " + id);
            });
    }
    
}


