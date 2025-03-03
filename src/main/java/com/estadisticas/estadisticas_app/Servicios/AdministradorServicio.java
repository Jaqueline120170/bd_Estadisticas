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
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;

@Service
public class AdministradorServicio {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorServicio.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

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
                usuario.setTipoSuscripcion(usuarioActualizado.getTipoSuscripcion());
                usuario.setEstadoSuscripcion(usuarioActualizado.getEstadoSuscripcion());
                usuario.setFechaInicioSuscripcion(usuarioActualizado.getFechaInicioSuscripcion());
                usuario.setFechaFinSuscripcion(usuarioActualizado.getFechaFinSuscripcion());

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
     */
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            logger.warn("Intento de eliminar usuario no encontrado con ID: {}", id);
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + id);
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

        long totalUsuarios = usuarioRepository.count();
        long usuariosVerificados = usuarioRepository.countByVerificado(true);
        long usuariosNoVerificados = totalUsuarios - usuariosVerificados;
        long admins = usuarioRepository.countByRolUsuario("ADMIN");
        long usuarios = usuarioRepository.countByRolUsuario("USUARIO");

        long suscripcionesFree = usuarioRepository.countByTipoSuscripcion("FREE");
        long suscripcionesPremium = usuarioRepository.countByTipoSuscripcion("PREMIUM");
        long suscripcionesActivas = usuarioRepository.countByEstadoSuscripcion("ACTIVA");
        long suscripcionesInactivas = usuarioRepository.countByEstadoSuscripcion("INACTIVA");

        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("Total de Usuarios", totalUsuarios);
        estadisticas.put("Usuarios Verificados", usuariosVerificados);
        estadisticas.put("Usuarios No Verificados", usuariosNoVerificados);
        estadisticas.put("Administradores", admins);
        estadisticas.put("Usuarios", usuarios);
        estadisticas.put("Suscripciones Free", suscripcionesFree);
        estadisticas.put("Suscripciones Premium", suscripcionesPremium);
        estadisticas.put("Suscripciones Activas", suscripcionesActivas);
        estadisticas.put("Suscripciones Inactivas", suscripcionesInactivas);

        return estadisticas;
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado.
     */
    public Usuario buscarUsuarioPorId(Long id) {
        logger.info("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Usuario no encontrado con ID: {}", id);
                return new IllegalArgumentException("Usuario no encontrado con ID: " + id);
            });
    }
}


