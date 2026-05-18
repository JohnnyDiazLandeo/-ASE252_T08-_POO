package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Curso;
import vallegrande.edu.pe.model.CursoDAO;

import java.sql.SQLException;
import java.util.List;

public class CursoController {
    private final CursoDAO cursoDAO;

    public CursoController() {
        this.cursoDAO = new CursoDAO();
    }

    public void crearCurso(Curso curso) throws SQLException {
        cursoDAO.insertar(curso);
    }

    public void actualizarCurso(Curso curso) throws SQLException {
        cursoDAO.actualizar(curso);
    }

    public void desactivarCurso(int idCurso) throws SQLException {
        cursoDAO.desactivar(idCurso);
    }

    public void activarCurso(int idCurso) throws SQLException {
        cursoDAO.activar(idCurso);
    }

    public List<Curso> listarCursos(boolean incluirInactivos) throws SQLException {
        return cursoDAO.listar(incluirInactivos);
    }

    public List<Curso> buscarCursos(String texto, boolean incluirInactivos) throws SQLException {
        return cursoDAO.buscar(texto, incluirInactivos);
    }

    public boolean existeCodigoCurso(String codigo, Integer idCursoExcluir) throws SQLException {
        return cursoDAO.existeCodigo(codigo, idCursoExcluir);
    }
}
