package com.estadisticas.estadisticas_app.Utils;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Clase utilitaria que proporciona métodos para realizar validaciones comunes.
 * Estas validaciones incluyen la comprobación de campos vacíos, el formato de un correo electrónico
 * y la validez de contraseñas.
 */
public class ValidacionesUtil {

    /**
     * Valida que un campo no esté vacío o nulo.
     * 
     * @param campo el valor del campo a validar.
     * @param nombreCampo el nombre del campo (usado en el mensaje de error).
     * @throws IllegalArgumentException si el campo es nulo o vacío.
     */
    public static void validarNoVacio(String campo, String nombreCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío.");
        }
    }

    /**
     * Valida el formato de un email utilizando una librería externa.
     * El email debe tener un formato válido para ser aceptado.
     * 
     * @param email el email a validar.
     * @throws IllegalArgumentException si el email es nulo, vacío o tiene un formato inválido.
     */
    public static void validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("El email tiene un formato inválido.");
        }
    }

    /**
     * Valida que una contraseña tenga al menos 6 caracteres.
     * Esta es una validación básica para garantizar contraseñas mínimamente seguras.
     * 
     * @param password la contraseña a validar.
     * @throws IllegalArgumentException si la contraseña es nula o tiene menos de 6 caracteres.
     */
    public static void validarPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
    }
}
