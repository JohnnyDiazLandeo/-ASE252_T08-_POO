package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Usuario;
import vallegrande.edu.pe.model.UsuarioDAO;

import java.sql.SQLException;

public class LoginController {
    private final UsuarioDAO usuarioDAO;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) throws SQLException {
        Usuario usuario = usuarioDAO.validarUsuario(nombreUsuario, contrasena);
        if (usuario != null) {
            usuarioDAO.actualizarUltimoAcceso(usuario.getIdUsuario());
        }
        return usuario;
    }
}
