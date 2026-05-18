package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Curso;
import vallegrande.edu.pe.model.Estudiante;
import vallegrande.edu.pe.model.Matricula;
import vallegrande.edu.pe.model.MatriculaDAO;

import java.sql.SQLException;
import java.util.List;

public class MatriculaController {
    private final MatriculaDAO matriculaDAO;

    public MatriculaController() {
        this.matriculaDAO = new MatriculaDAO();
    }

    public void crearMatricula(Matricula matricula) throws SQLException {
        matriculaDAO.insertar(matricula);
    }

    public void actualizarMatricula(Matricula matricula) throws SQLException {
        matriculaDAO.actualizar(matricula);
    }

    public void desactivarMatricula(int idMatricula) throws SQLException {
        matriculaDAO.desactivar(idMatricula);
    }

    public void activarMatricula(int idMatricula) throws SQLException {
        matriculaDAO.activar(idMatricula);
    }

    public List<Matricula> listarMatriculas(boolean incluirInactivos) throws SQLException {
        return matriculaDAO.listar(incluirInactivos);
    }

    public List<Estudiante> listarEstudiantesActivos() throws SQLException {
        return matriculaDAO.listarEstudiantesActivos();
    }

    public List<Curso> listarCursosActivos() throws SQLException {
        return matriculaDAO.listarCursosActivos();
    }

    public boolean existeMatricula(int idEstudiante, int idCurso, String ciclo, Integer idMatriculaExcluir) throws SQLException {
        return matriculaDAO.existeMatricula(idEstudiante, idCurso, ciclo, idMatriculaExcluir);
    }
}
