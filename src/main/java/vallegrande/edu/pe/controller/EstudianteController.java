package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Estudiante;
import vallegrande.edu.pe.model.EstudianteDAO;

import java.sql.SQLException;
import java.util.List;

public class EstudianteController {
    private final EstudianteDAO estudianteDAO;

    public EstudianteController() {
        this.estudianteDAO = new EstudianteDAO();
    }

    public void crearEstudiante(Estudiante estudiante) throws SQLException {
        estudianteDAO.insertar(estudiante);
    }

    public void actualizarEstudiante(Estudiante estudiante) throws SQLException {
        estudianteDAO.actualizar(estudiante);
    }

    public void desactivarEstudiante(int idEstudiante) throws SQLException {
        estudianteDAO.desactivar(idEstudiante);
    }

    public void activarEstudiante(int idEstudiante) throws SQLException {
        estudianteDAO.activar(idEstudiante);
    }

    public List<Estudiante> listarEstudiantes(boolean incluirInactivos) throws SQLException {
        return estudianteDAO.listar(incluirInactivos);
    }

    public List<Estudiante> buscarEstudiantes(String texto, boolean incluirInactivos) throws SQLException {
        return estudianteDAO.buscar(texto, incluirInactivos);
    }

    public boolean existeNumeroDocumento(String numeroDocumento, Integer idEstudianteExcluir) throws SQLException {
        return estudianteDAO.existeNumeroDocumento(numeroDocumento, idEstudianteExcluir);
    }
}
