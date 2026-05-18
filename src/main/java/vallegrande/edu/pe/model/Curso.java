package vallegrande.edu.pe.model;

import java.time.LocalDate;

public class Curso {
    private int idCurso;
    private String codigo;
    private String nombre;
    private int creditos;
    private int horasSemana;
    private String descripcion;
    private LocalDate fechaRegistro;
    private boolean activo;

    public Curso() {
    }

    public Curso(int idCurso, String codigo, String nombre, int creditos, int horasSemana, String descripcion, LocalDate fechaRegistro, boolean activo) {
        this.idCurso = idCurso;
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.horasSemana = horasSemana;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(int horasSemana) {
        this.horasSemana = horasSemana;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
