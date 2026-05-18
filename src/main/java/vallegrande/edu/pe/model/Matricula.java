package vallegrande.edu.pe.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Matricula {
    private int idMatricula;
    private int idEstudiante;
    private int idCurso;
    private LocalDate fechaMatricula;
    private String ciclo;
    private BigDecimal notaFinal;
    private String estadoMatricula;
    private boolean activo;

    private String nombreEstudiante;
    private String nombreCurso;

    public Matricula() {
    }

    public Matricula(int idMatricula, int idEstudiante, int idCurso, LocalDate fechaMatricula, String ciclo, BigDecimal notaFinal, String estadoMatricula, boolean activo) {
        this.idMatricula = idMatricula;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.fechaMatricula = fechaMatricula;
        this.ciclo = ciclo;
        this.notaFinal = notaFinal;
        this.estadoMatricula = estadoMatricula;
        this.activo = activo;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public BigDecimal getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(BigDecimal notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getEstadoMatricula() {
        return estadoMatricula;
    }

    public void setEstadoMatricula(String estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}
