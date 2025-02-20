package com.estadisticas.estadisticas_app.Servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estadisticas.estadisticas_app.Modelos.Usuario;
import com.estadisticas.estadisticas_app.Repositorios.UsuarioRepository;

@Service
public class AdministradorServicio {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario modificarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
                usuario.setEmailUsuario(usuarioActualizado.getEmailUsuario());
                usuario.setTelefonoUsuario(usuarioActualizado.getTelefonoUsuario());
                usuario.setRolUsuario(usuarioActualizado.getRolUsuario());
                usuario.setVerificado(usuarioActualizado.isVerificado());
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con el ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public Map<String, Long> obtenerEstadisticasUsuarios() {
        long totalUsuarios = usuarioRepository.count();
        long usuariosVerificados = usuarioRepository.countByVerificado(true);
        long usuariosNoVerificados = totalUsuarios - usuariosVerificados;

        Map<String, Long> estadisticas = new HashMap<>();
        estadisticas.put("Total de Usuarios", totalUsuarios);
        estadisticas.put("Usuarios Verificados", usuariosVerificados);
        estadisticas.put("Usuarios No Verificados", usuariosNoVerificados);

        return estadisticas;
    }
}

