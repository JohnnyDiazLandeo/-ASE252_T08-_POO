package modelo;

import java.util.List;

/**
 * Clase modelo que representa un Estudiante.
 * Aplica ENCAPSULAMIENTO: todos los atributos son privados,
 * accesibles únicamente mediante getters y setters.
 */
public class Estudiante {

    // ── Atributos privados (Encapsulamiento) ─────────────────────────────────
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String carrera;
    private List<String> intereses;

    // ── Constructor completo ──────────────────────────────────────────────────
    public Estudiante(String codigo, String nombre, String apellido,
                      String correo, String carrera, List<String> intereses) {
        this.codigo    = codigo;
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.correo    = correo;
        this.carrera   = carrera;
        this.intereses = intereses;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getCodigo()          { return codigo; }
    public String getNombre()          { return nombre; }
    public String getApellido()        { return apellido; }
    public String getCorreo()          { return correo; }
    public String getCarrera()         { return carrera; }
    public List<String> getIntereses() { return intereses; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setCodigo(String codigo)            { this.codigo    = codigo; }
    public void setNombre(String nombre)            { this.nombre    = nombre; }
    public void setApellido(String apellido)        { this.apellido  = apellido; }
    public void setCorreo(String correo)            { this.correo    = correo; }
    public void setCarrera(String carrera)          { this.carrera   = carrera; }
    public void setIntereses(List<String> intereses){ this.intereses = intereses; }

    // ── Método utilitario ─────────────────────────────────────────────────────
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Representación en texto del estudiante.
     * Sobrescribe Object.toString() — POLIMORFISMO.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %s",
                codigo, getNombreCompleto(), carrera, correo);
    }
}
