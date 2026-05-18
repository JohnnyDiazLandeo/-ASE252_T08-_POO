package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.CursoController;
import vallegrande.edu.pe.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelCurso extends JPanel {
    private final CursoController cursoController;
    private JTable tablaCursos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JCheckBox chkMostrarInactivos;
    private JButton btnNuevo, btnEditar, btnDesactivarActivar, btnBuscar;

    private static final Color COLOR_PRIMARIO = new Color(10, 60, 120);
    private static final Color COLOR_SECUNDARIO = new Color(25, 90, 160);
    private static final Color COLOR_FONDO = new Color(30, 50, 80);
    private static final Color COLOR_FONDO_CLARO = new Color(40, 70, 110);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_TEXTO_OSCURO = new Color(200, 220, 255);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_GRIS = new Color(180, 180, 180);

    public PanelCurso() {
        this.cursoController = new CursoController();
        inicializarComponentes();
        cargarCursos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new BorderLayout(15, 15));
        panelSuperior.setBackground(COLOR_FONDO);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        panelBusqueda.setBackground(COLOR_FONDO);
        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscar.setForeground(COLOR_TEXTO);
        panelBusqueda.add(lblBuscar);
        txtBuscar = new JTextField(28);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBackground(COLOR_FONDO_CLARO);
        txtBuscar.setForeground(COLOR_TEXTO);
        txtBuscar.setCaretColor(COLOR_TEXTO);
        txtBuscar.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setBackground(COLOR_SECUNDARIO);
        btnBuscar.setForeground(COLOR_TEXTO);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setPreferredSize(new Dimension(110, 35));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscarCursos());
        panelBusqueda.add(btnBuscar);
        chkMostrarInactivos = new JCheckBox("Mostrar inactivos");
        chkMostrarInactivos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkMostrarInactivos.setBackground(COLOR_FONDO);
        chkMostrarInactivos.setForeground(COLOR_TEXTO);
        chkMostrarInactivos.addActionListener(e -> cargarCursos());
        panelBusqueda.add(chkMostrarInactivos);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        panelBotones.setBackground(COLOR_FONDO);
        btnNuevo = new JButton("➕ Nuevo");
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNuevo.setBackground(COLOR_SECUNDARIO);
        btnNuevo.setForeground(COLOR_TEXTO);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setPreferredSize(new Dimension(130, 35));
        btnNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevo.addActionListener(e -> nuevoCurso());
        btnEditar = new JButton("✏️ Editar");
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEditar.setBackground(new Color(46, 125, 50));
        btnEditar.setForeground(COLOR_TEXTO);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setPreferredSize(new Dimension(130, 35));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.addActionListener(e -> editarCurso());
        btnDesactivarActivar = new JButton("🔄 Desactivar/Activar");
        btnDesactivarActivar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDesactivarActivar.setBackground(new Color(245, 124, 0));
        btnDesactivarActivar.setForeground(COLOR_TEXTO);
        btnDesactivarActivar.setFocusPainted(false);
        btnDesactivarActivar.setBorderPainted(false);
        btnDesactivarActivar.setPreferredSize(new Dimension(200, 35));
        btnDesactivarActivar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDesactivarActivar.addActionListener(e -> desactivarActivarCurso());
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnDesactivarActivar);

        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(panelBotones, BorderLayout.EAST);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Código", "Nombre", "Créditos", "Horas/Semana", "Fecha Registro", "Activo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCursos = new JTable(modeloTabla);
        tablaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCursos.setRowHeight(35);
        tablaCursos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaCursos.setBackground(COLOR_FONDO_CLARO);
        tablaCursos.setForeground(COLOR_TEXTO);
        tablaCursos.setGridColor(new Color(60, 90, 130));
        tablaCursos.setSelectionBackground(COLOR_SECUNDARIO);
        tablaCursos.setSelectionForeground(COLOR_TEXTO);
        JTableHeader header = tablaCursos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(COLOR_TEXTO);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        tablaCursos.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarCursos() {
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Curso> cursos = cursoController.listarCursos(incluirInactivos);
            actualizarTabla(cursos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al cargar cursos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCursos() {
        String texto = txtBuscar.getText().trim();
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Curso> cursos;
            if (texto.isEmpty()) {
                cursos = cursoController.listarCursos(incluirInactivos);
            } else {
                cursos = cursoController.buscarCursos(texto, incluirInactivos);
            }
            actualizarTabla(cursos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al buscar cursos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(List<Curso> cursos) {
        modeloTabla.setRowCount(0);
        for (Curso curso : cursos) {
            modeloTabla.addRow(new Object[]{
                    curso.getIdCurso(),
                    curso.getCodigo(),
                    curso.getNombre(),
                    curso.getCreditos(),
                    curso.getHorasSemana(),
                    curso.getFechaRegistro(),
                    curso.isActivo() ? "✅ Sí" : "❌ No"
            });
        }
    }

    private void nuevoCurso() {
        DialogoCurso dialogo = new DialogoCurso((JFrame) SwingUtilities.getWindowAncestor(this), cursoController, null, this);
        dialogo.setVisible(true);
    }

    private void editarCurso() {
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor seleccione un curso", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCurso = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Curso> cursos = cursoController.listarCursos(incluirInactivos);
            Curso cursoSeleccionado = cursos.stream()
                    .filter(c -> c.getIdCurso() == idCurso)
                    .findFirst()
                    .orElse(null);

            if (cursoSeleccionado != null) {
                DialogoCurso dialogo = new DialogoCurso((JFrame) SwingUtilities.getWindowAncestor(this), cursoController, cursoSeleccionado, this);
                dialogo.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desactivarActivarCurso() {
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor seleccione un curso", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCurso = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean esActivo = ((String) modeloTabla.getValueAt(filaSeleccionada, 6)).contains("✅");
        String accion = esActivo ? "desactivar" : "activar";

        int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de " + accion + " este curso?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                if (esActivo) {
                    cursoController.desactivarCurso(idCurso);
                } else {
                    cursoController.activarCurso(idCurso);
                }
                cargarCursos();
                JOptionPane.showMessageDialog(this, "✅ Curso " + accion + "do correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
