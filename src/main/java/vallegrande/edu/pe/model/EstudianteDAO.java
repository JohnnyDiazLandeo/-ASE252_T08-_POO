package vallegrande.edu.pe.model;

import vallegrande.edu.pe.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    public void insertar(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiante (tipo_documento, numero_documento, nombres, apellidos, email, telefono, direccion, fecha_nacimiento, genero, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, estudiante.getTipoDocumento());
            pstmt.setString(2, estudiante.getNumeroDocumento());
            pstmt.setString(3, estudiante.getNombres());
            pstmt.setString(4, estudiante.getApellidos());
            pstmt.setString(5, estudiante.getEmail());
            pstmt.setString(6, estudiante.getTelefono());
            pstmt.setString(7, estudiante.getDireccion());
            if (estudiante.getFechaNacimiento() != null) {
                pstmt.setDate(8, Date.valueOf(estudiante.getFechaNacimiento()));
            } else {
                pstmt.setNull(8, Types.DATE);
            }
            pstmt.setString(9, estudiante.getGenero());
            pstmt.setBoolean(10, estudiante.isActivo());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    estudiante.setIdEstudiante(rs.getInt(1));
                }
            }
        }
    }

    public void actualizar(Estudiante estudiante) throws SQLException {
        String sql = "UPDATE estudiante SET tipo_documento = ?, numero_documento = ?, nombres = ?, apellidos = ?, email = ?, telefono = ?, direccion = ?, fecha_nacimiento = ?, genero = ?, activo = ? WHERE id_estudiante = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getTipoDocumento());
            pstmt.setString(2, estudiante.getNumeroDocumento());
            pstmt.setString(3, estudiante.getNombres());
            pstmt.setString(4, estudiante.getApellidos());
            pstmt.setString(5, estudiante.getEmail());
            pstmt.setString(6, estudiante.getTelefono());
            pstmt.setString(7, estudiante.getDireccion());
            if (estudiante.getFechaNacimiento() != null) {
                pstmt.setDate(8, Date.valueOf(estudiante.getFechaNacimiento()));
            } else {
                pstmt.setNull(8, Types.DATE);
            }
            pstmt.setString(9, estudiante.getGenero());
            pstmt.setBoolean(10, estudiante.isActivo());
            pstmt.setInt(11, estudiante.getIdEstudiante());
            pstmt.executeUpdate();
        }
    }

    public void desactivar(int idEstudiante) throws SQLException {
        String sql = "UPDATE estudiante SET activo = FALSE WHERE id_estudiante = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);
            pstmt.executeUpdate();
        }
    }

    public void activar(int idEstudiante) throws SQLException {
        String sql = "UPDATE estudiante SET activo = TRUE WHERE id_estudiante = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);
            pstmt.executeUpdate();
        }
    }

    public List<Estudiante> listar(boolean incluirInactivos) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = incluirInactivos ? "SELECT * FROM estudiante" : "SELECT * FROM estudiante WHERE activo = TRUE";
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidos(rs.getString("apellidos"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setDireccion(rs.getString("direccion"));
                Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                if (fechaNacimiento != null) {
                    estudiante.setFechaNacimiento(fechaNacimiento.toLocalDate());
                }
                estudiante.setGenero(rs.getString("genero"));
                estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                estudiante.setActivo(rs.getBoolean("activo"));
                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    public List<Estudiante> buscar(String texto, boolean incluirInactivos) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = (incluirInactivos 
                ? "SELECT * FROM estudiante WHERE numero_documento LIKE ? OR nombres LIKE ? OR apellidos LIKE ?" 
                : "SELECT * FROM estudiante WHERE activo = TRUE AND (numero_documento LIKE ? OR nombres LIKE ? OR apellidos LIKE ?)");
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String patron = "%" + texto + "%";
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);
            pstmt.setString(3, patron);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                    estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                    estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                    estudiante.setNombres(rs.getString("nombres"));
                    estudiante.setApellidos(rs.getString("apellidos"));
                    estudiante.setEmail(rs.getString("email"));
                    estudiante.setTelefono(rs.getString("telefono"));
                    estudiante.setDireccion(rs.getString("direccion"));
                    Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                    if (fechaNacimiento != null) {
                        estudiante.setFechaNacimiento(fechaNacimiento.toLocalDate());
                    }
                    estudiante.setGenero(rs.getString("genero"));
                    estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                    estudiante.setActivo(rs.getBoolean("activo"));
                    estudiantes.add(estudiante);
                }
            }
        }
        return estudiantes;
    }

    public boolean existeNumeroDocumento(String numeroDocumento, Integer idEstudianteExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM estudiante WHERE numero_documento = ?";
        if (idEstudianteExcluir != null) {
            sql += " AND id_estudiante != ?";
        }
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroDocumento);
            if (idEstudianteExcluir != null) {
                pstmt.setInt(2, idEstudianteExcluir);
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
