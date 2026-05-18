package vallegrande.edu.pe.model;

import vallegrande.edu.pe.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public void insertar(Curso curso) throws SQLException {
        String sql = "INSERT INTO curso (codigo, nombre, creditos, horas_semana, descripcion, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getNombre());
            pstmt.setInt(3, curso.getCreditos());
            pstmt.setInt(4, curso.getHorasSemana());
            pstmt.setString(5, curso.getDescripcion());
            pstmt.setBoolean(6, curso.isActivo());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setIdCurso(rs.getInt(1));
                }
            }
        }
    }

    public void actualizar(Curso curso) throws SQLException {
        String sql = "UPDATE curso SET codigo = ?, nombre = ?, creditos = ?, horas_semana = ?, descripcion = ?, activo = ? WHERE id_curso = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getNombre());
            pstmt.setInt(3, curso.getCreditos());
            pstmt.setInt(4, curso.getHorasSemana());
            pstmt.setString(5, curso.getDescripcion());
            pstmt.setBoolean(6, curso.isActivo());
            pstmt.setInt(7, curso.getIdCurso());
            pstmt.executeUpdate();
        }
    }

    public void desactivar(int idCurso) throws SQLException {
        String sql = "UPDATE curso SET activo = FALSE WHERE id_curso = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCurso);
            pstmt.executeUpdate();
        }
    }

    public void activar(int idCurso) throws SQLException {
        String sql = "UPDATE curso SET activo = TRUE WHERE id_curso = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCurso);
            pstmt.executeUpdate();
        }
    }

    public List<Curso> listar(boolean incluirInactivos) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = incluirInactivos ? "SELECT * FROM curso" : "SELECT * FROM curso WHERE activo = TRUE";
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                curso.setCreditos(rs.getInt("creditos"));
                curso.setHorasSemana(rs.getInt("horas_semana"));
                curso.setDescripcion(rs.getString("descripcion"));
                curso.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                curso.setActivo(rs.getBoolean("activo"));
                cursos.add(curso);
            }
        }
        return cursos;
    }

    public List<Curso> buscar(String texto, boolean incluirInactivos) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = (incluirInactivos 
                ? "SELECT * FROM curso WHERE codigo LIKE ? OR nombre LIKE ?" 
                : "SELECT * FROM curso WHERE activo = TRUE AND (codigo LIKE ? OR nombre LIKE ?)");
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String patron = "%" + texto + "%";
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("id_curso"));
                    curso.setCodigo(rs.getString("codigo"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setCreditos(rs.getInt("creditos"));
                    curso.setHorasSemana(rs.getInt("horas_semana"));
                    curso.setDescripcion(rs.getString("descripcion"));
                    curso.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
                    curso.setActivo(rs.getBoolean("activo"));
                    cursos.add(curso);
                }
            }
        }
        return cursos;
    }

    public boolean existeCodigo(String codigo, Integer idCursoExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM curso WHERE codigo = ?";
        if (idCursoExcluir != null) {
            sql += " AND id_curso != ?";
        }
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            if (idCursoExcluir != null) {
                pstmt.setInt(2, idCursoExcluir);
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
