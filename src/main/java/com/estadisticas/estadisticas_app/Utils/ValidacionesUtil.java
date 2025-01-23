package com.estadisticas.estadisticas_app.Utils;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidacionesUtil {

    // Validar que un campo no esté vacío o nulo
    public static void validarNoVacio(String campo, String nombreCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío.");
        }
    }

    // Validar el formato del email
    public static void validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("El email tiene un formato inválido.");
        }
    }

    // Validar contraseña (ejemplo: mínimo 6 caracteres)
    public static void validarPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
    }
}
