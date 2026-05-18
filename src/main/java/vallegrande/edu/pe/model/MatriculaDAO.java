package vallegrande.edu.pe.model;

import vallegrande.edu.pe.ConexionMySQL;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    public void insertar(Matricula matricula) throws SQLException {
        String sql = "INSERT INTO matricula (id_estudiante, id_curso, fecha_matricula, ciclo, nota_final, estado_matricula, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, matricula.getIdEstudiante());
            pstmt.setInt(2, matricula.getIdCurso());
            pstmt.setDate(3, Date.valueOf(matricula.getFechaMatricula()));
            pstmt.setString(4, matricula.getCiclo());
            if (matricula.getNotaFinal() != null) {
                pstmt.setBigDecimal(5, matricula.getNotaFinal());
            } else {
                pstmt.setNull(5, Types.DECIMAL);
            }
            pstmt.setString(6, matricula.getEstadoMatricula());
            pstmt.setBoolean(7, matricula.isActivo());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    matricula.setIdMatricula(rs.getInt(1));
                }
            }
        }
    }

    public void actualizar(Matricula matricula) throws SQLException {
        String sql = "UPDATE matricula SET id_estudiante = ?, id_curso = ?, fecha_matricula = ?, ciclo = ?, nota_final = ?, estado_matricula = ?, activo = ? WHERE id_matricula = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, matricula.getIdEstudiante());
            pstmt.setInt(2, matricula.getIdCurso());
            pstmt.setDate(3, Date.valueOf(matricula.getFechaMatricula()));
            pstmt.setString(4, matricula.getCiclo());
            if (matricula.getNotaFinal() != null) {
                pstmt.setBigDecimal(5, matricula.getNotaFinal());
            } else {
                pstmt.setNull(5, Types.DECIMAL);
            }
            pstmt.setString(6, matricula.getEstadoMatricula());
            pstmt.setBoolean(7, matricula.isActivo());
            pstmt.setInt(8, matricula.getIdMatricula());
            pstmt.executeUpdate();
        }
    }

    public void desactivar(int idMatricula) throws SQLException {
        String sql = "UPDATE matricula SET activo = FALSE WHERE id_matricula = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMatricula);
            pstmt.executeUpdate();
        }
    }

    public void activar(int idMatricula) throws SQLException {
        String sql = "UPDATE matricula SET activo = TRUE WHERE id_matricula = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMatricula);
            pstmt.executeUpdate();
        }
    }

    public List<Matricula> listar(boolean incluirInactivos) throws SQLException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = incluirInactivos 
                ? "SELECT m.*, e.nombres, e.apellidos, c.nombre as nombre_curso FROM matricula m " +
                  "JOIN estudiante e ON m.id_estudiante = e.id_estudiante " +
                  "JOIN curso c ON m.id_curso = c.id_curso" 
                : "SELECT m.*, e.nombres, e.apellidos, c.nombre as nombre_curso FROM matricula m " +
                  "JOIN estudiante e ON m.id_estudiante = e.id_estudiante " +
                  "JOIN curso c ON m.id_curso = c.id_curso WHERE m.activo = TRUE";
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula").toLocalDate());
                matricula.setCiclo(rs.getString("ciclo"));
                matricula.setNotaFinal(rs.getBigDecimal("nota_final"));
                matricula.setEstadoMatricula(rs.getString("estado_matricula"));
                matricula.setActivo(rs.getBoolean("activo"));
                matricula.setNombreEstudiante(rs.getString("nombres") + " " + rs.getString("apellidos"));
                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matriculas.add(matricula);
            }
        }
        return matriculas;
    }

    public List<Estudiante> listarEstudiantesActivos() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM estudiante WHERE activo = TRUE";
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidos(rs.getString("apellidos"));
                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    public List<Curso> listarCursosActivos() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM curso WHERE activo = TRUE";
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                cursos.add(curso);
            }
        }
        return cursos;
    }

    public boolean existeMatricula(int idEstudiante, int idCurso, String ciclo, Integer idMatriculaExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM matricula WHERE id_estudiante = ? AND id_curso = ? AND ciclo = ?";
        if (idMatriculaExcluir != null) {
            sql += " AND id_matricula != ?";
        }
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);
            pstmt.setInt(2, idCurso);
            pstmt.setString(3, ciclo);
            if (idMatriculaExcluir != null) {
                pstmt.setInt(4, idMatriculaExcluir);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
