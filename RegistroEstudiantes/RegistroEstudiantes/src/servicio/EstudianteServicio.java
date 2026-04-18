package servicio;

import modelo.Estudiante;
import util.Validador;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de servicio que gestiona la lógica de negocio para Estudiantes.
 * Aplica ABSTRACCIÓN: separa la lógica de negocio de la interfaz gráfica.
 * Aplica ENCAPSULAMIENTO: la lista interna de registros es privada.
 */
public class EstudianteServicio {

    // ── Estado interno encapsulado ────────────────────────────────────────────
    private final List<Estudiante> registros;
    private int contadorCodigo;

    // ── Constructor ───────────────────────────────────────────────────────────
    public EstudianteServicio() {
        this.registros       = new ArrayList<>();
        this.contadorCodigo  = 1;
    }

    /**
     * Genera el siguiente código de estudiante en formato EST-YYYY-NNN.
     * @return Código generado
     */
    public String generarCodigo() {
        int anio = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return String.format("EST-%d-%03d", anio, contadorCodigo);
    }

    /**
     * Registra un nuevo estudiante después de validar sus datos.
     * @param nombre    Nombre del estudiante
     * @param apellido  Apellido del estudiante
     * @param correo    Correo electrónico
     * @param carrera   Carrera universitaria
     * @param intereses Lista de áreas de interés seleccionadas
     * @return El objeto Estudiante creado y registrado
     * @throws IllegalArgumentException si algún campo no pasa la validación
     */
    public Estudiante registrarEstudiante(String nombre, String apellido,
                                          String correo, String carrera,
                                          List<String> intereses) {
        // Validaciones con mensajes descriptivos
        if (!Validador.soloLetras(nombre)) {
            throw new IllegalArgumentException("El nombre solo debe contener letras.");
        }
        if (!Validador.soloLetras(apellido)) {
            throw new IllegalArgumentException("El apellido solo debe contener letras.");
        }
        if (!Validador.esCorreoValido(correo)) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }
        if (!Validador.tieneLongitudMinima(carrera, 3)) {
            throw new IllegalArgumentException("La carrera debe tener al menos 3 caracteres.");
        }
        if (intereses == null || intereses.isEmpty()) {
            throw new IllegalArgumentException("Debes seleccionar al menos un área de interés.");
        }

        // Crear y almacenar el estudiante
        String codigo    = generarCodigo();
        Estudiante nuevo = new Estudiante(codigo, nombre.trim(), apellido.trim(),
                                          correo.trim(), carrera.trim(), intereses);
        registros.add(nuevo);
        contadorCodigo++;
        return nuevo;
    }

    /**
     * Devuelve una copia de la lista de todos los estudiantes registrados.
     * @return Lista de estudiantes (copia defensiva)
     */
    public List<Estudiante> obtenerTodos() {
        return new ArrayList<>(registros);
    }

    /**
     * Devuelve el número total de estudiantes registrados.
     * @return Cantidad de registros
     */
    public int totalRegistros() {
        return registros.size();
    }

    /**
     * Genera un resumen en texto de un estudiante para mostrar en diálogo.
     * @param e Estudiante a resumir
     * @return Cadena formateada con los datos del estudiante
     */
    public String generarResumen(Estudiante e) {
        StringBuilder sb = new StringBuilder();
        sb.append("✔ Estudiante registrado exitosamente\n");
        sb.append("─────────────────────────────────\n");
        sb.append(String.format("Código:    %s%n", e.getCodigo()));
        sb.append(String.format("Nombre:    %s%n", e.getNombreCompleto()));
        sb.append(String.format("Correo:    %s%n", e.getCorreo()));
        sb.append(String.format("Carrera:   %s%n", e.getCarrera()));
        sb.append(String.format("Intereses: %s%n", String.join(", ", e.getIntereses())));
        sb.append("─────────────────────────────────\n");
        sb.append(String.format("Total registros: %d", totalRegistros()));
        return sb.toString();
    }
}
