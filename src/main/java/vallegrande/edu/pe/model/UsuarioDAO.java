package vallegrande.edu.pe.model;

import vallegrande.edu.pe.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario validarUsuario(String nombreUsuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nombre_usuario = ? AND contrasena = ? AND activo = TRUE";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasena);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setActivo(rs.getBoolean("activo"));
                    usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    if (rs.getTimestamp("ultimo_acceso") != null) {
                        usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso").toLocalDateTime());
                    }
                    return usuario;
                }
            }
        }
        return null;
    }

    public void actualizarUltimoAcceso(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET ultimo_acceso = NOW() WHERE id_usuario = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.executeUpdate();
        }
    }
}
