package util;

/**
 * Clase de utilidades para validación de datos del formulario.
 * Aplica ABSTRACCIÓN: métodos estáticos reutilizables que ocultan
 * la lógica de validación al resto de la aplicación.
 */
public class Validador {

    // Constructor privado: clase de utilidad, no instanciable
    private Validador() {}

    /**
     * Verifica que un texto no esté vacío o nulo.
     * @param texto Cadena a verificar
     * @return true si el texto tiene contenido
     */
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Verifica que un correo tenga formato básico válido.
     * Comprueba la presencia de @ y al menos un punto después.
     * @param correo Correo electrónico a validar
     * @return true si el formato es válido
     */
    public static boolean esCorreoValido(String correo) {
        if (!esTextoValido(correo)) return false;
        int at = correo.indexOf('@');
        if (at <= 0) return false;
        int punto = correo.indexOf('.', at);
        return punto > at + 1 && punto < correo.length() - 1;
    }

    /**
     * Verifica que un texto tenga al menos una longitud mínima.
     * @param texto  Cadena a verificar
     * @param minimo Longitud mínima requerida
     * @return true si el texto cumple la longitud mínima
     */
    public static boolean tieneLongitudMinima(String texto, int minimo) {
        return esTextoValido(texto) && texto.trim().length() >= minimo;
    }

    /**
     * Verifica que un texto contenga solo letras y espacios.
     * Útil para validar nombres y apellidos.
     * @param texto Cadena a verificar
     * @return true si solo contiene letras y espacios
     */
    public static boolean soloLetras(String texto) {
        if (!esTextoValido(texto)) return false;
        return texto.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
}
