package vallegrande.edu.pe.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Validador {

    public static boolean validarNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            return false;
        }
        switch (tipoDocumento) {
            case "DNI":
                return numeroDocumento.matches("\\d{8}");
            case "CE":
                return numeroDocumento.matches("\\d{9}");
            case "Pasaporte":
                return numeroDocumento.length() >= 5 && numeroDocumento.length() <= 20;
            default:
                return false;
        }
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }

    public static boolean validarCredito(int creditos) {
        return creditos >= 1 && creditos <= 10;
    }

    public static boolean validarHoras(int horas) {
        return horas >= 1 && horas <= 40;
    }

    public static boolean validarNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarCodigoCurso(String codigo) {
        if (codigo == null || codigo.length() < 3 || codigo.length() > 10) {
            return false;
        }
        return codigo.matches("[A-Z0-9]+");
    }

    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            return true;
        }
        return telefono.matches("\\d{7,15}");
    }

    public static boolean validarNota(BigDecimal nota) {
        if (nota == null) {
            return true;
        }
        return nota.compareTo(BigDecimal.ZERO) >= 0 && nota.compareTo(new BigDecimal("20")) <= 0;
    }

    public static boolean validarCiclo(String ciclo) {
        if (ciclo == null || ciclo.trim().isEmpty()) {
            return false;
        }
        return ciclo.matches("\\d{4}-[I]{1,2}");
    }
}
