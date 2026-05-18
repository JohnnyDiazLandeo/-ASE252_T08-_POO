package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.MatriculaController;
import vallegrande.edu.pe.model.Matricula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelMatricula extends JPanel {
    private final MatriculaController matriculaController;
    private JTable tablaMatriculas;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JCheckBox chkMostrarInactivos;
    private JButton btnNuevo, btnEditar, btnDesactivarActivar, btnBuscar;

    private static final Color COLOR_PRIMARIO = new Color(13, 71, 161);
    private static final Color COLOR_SECUNDARIO = new Color(21, 101, 192);
    private static final Color COLOR_FONDO = new Color(232, 234, 246);
    private static final Color COLOR_TEXTO = new Color(26, 35, 126);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_GRIS = new Color(66, 66, 66);

    public PanelMatricula() {
        this.matriculaController = new MatriculaController();
        inicializarComponentes();
        cargarMatriculas();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new BorderLayout(15, 15));
        panelSuperior.setBackground(COLOR_FONDO);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBackground(COLOR_FONDO);
        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBuscar.setForeground(COLOR_TEXTO);
        panelBusqueda.add(lblBuscar);
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscar.setBackground(COLOR_SECUNDARIO);
        btnBuscar.setForeground(COLOR_BLANCO);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setPreferredSize(new Dimension(100, 32));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscarMatriculas());
        panelBusqueda.add(btnBuscar);
        chkMostrarInactivos = new JCheckBox("Mostrar inactivos");
        chkMostrarInactivos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkMostrarInactivos.setBackground(COLOR_FONDO);
        chkMostrarInactivos.setForeground(COLOR_GRIS);
        chkMostrarInactivos.addActionListener(e -> cargarMatriculas());
        panelBusqueda.add(chkMostrarInactivos);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(COLOR_FONDO);
        btnNuevo = new JButton("➕ Nuevo");
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnNuevo.setBackground(COLOR_PRIMARIO);
        btnNuevo.setForeground(COLOR_BLANCO);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setPreferredSize(new Dimension(120, 32));
        btnNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevo.addActionListener(e -> nuevaMatricula());
        btnEditar = new JButton("✏️ Editar");
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEditar.setBackground(new Color(46, 125, 50));
        btnEditar.setForeground(COLOR_BLANCO);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setPreferredSize(new Dimension(120, 32));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.addActionListener(e -> editarMatricula());
        btnDesactivarActivar = new JButton("🔄 Desactivar/Activar");
        btnDesactivarActivar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDesactivarActivar.setBackground(new Color(245, 124, 0));
        btnDesactivarActivar.setForeground(COLOR_BLANCO);
        btnDesactivarActivar.setFocusPainted(false);
        btnDesactivarActivar.setBorderPainted(false);
        btnDesactivarActivar.setPreferredSize(new Dimension(180, 32));
        btnDesactivarActivar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDesactivarActivar.addActionListener(e -> desactivarActivarMatricula());
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnDesactivarActivar);

        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(panelBotones, BorderLayout.EAST);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Estudiante", "Curso", "Fecha Matrícula", "Ciclo", "Nota Final", "Estado", "Activo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMatriculas = new JTable(modeloTabla);
        tablaMatriculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMatriculas.setRowHeight(30);
        tablaMatriculas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaMatriculas.setGridColor(new Color(200, 200, 200));
        tablaMatriculas.setSelectionBackground(new Color(187, 222, 251));
        tablaMatriculas.setSelectionForeground(COLOR_GRIS);
        JTableHeader header = tablaMatriculas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(COLOR_BLANCO);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        tablaMatriculas.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaMatriculas);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarMatriculas() {
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Matricula> matriculas = matriculaController.listarMatriculas(incluirInactivos);
            actualizarTabla(matriculas);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al cargar matrículas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarMatriculas() {
        String texto = txtBuscar.getText().trim();
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Matricula> matriculas = matriculaController.listarMatriculas(incluirInactivos);
            if (!texto.isEmpty()) {
                matriculas = matriculas.stream()
                        .filter(m -> m.getNombreEstudiante().toLowerCase().contains(texto.toLowerCase())
                                || m.getNombreCurso().toLowerCase().contains(texto.toLowerCase())
                                || m.getCiclo().toLowerCase().contains(texto.toLowerCase()))
                        .toList();
            }
            actualizarTabla(matriculas);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al buscar matrículas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(List<Matricula> matriculas) {
        modeloTabla.setRowCount(0);
        for (Matricula matricula : matriculas) {
            modeloTabla.addRow(new Object[]{
                    matricula.getIdMatricula(),
                    matricula.getNombreEstudiante(),
                    matricula.getNombreCurso(),
                    matricula.getFechaMatricula(),
                    matricula.getCiclo(),
                    matricula.getNotaFinal(),
                    matricula.getEstadoMatricula(),
                    matricula.isActivo() ? "✅ Sí" : "❌ No"
            });
        }
    }

    private void nuevaMatricula() {
        DialogoMatricula dialogo = new DialogoMatricula((JFrame) SwingUtilities.getWindowAncestor(this), matriculaController, null, this);
        dialogo.setVisible(true);
    }

    private void editarMatricula() {
        int filaSeleccionada = tablaMatriculas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor seleccione una matrícula", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMatricula = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Matricula> matriculas = matriculaController.listarMatriculas(incluirInactivos);
            Matricula matriculaSeleccionada = matriculas.stream()
                    .filter(m -> m.getIdMatricula() == idMatricula)
                    .findFirst()
                    .orElse(null);

            if (matriculaSeleccionada != null) {
                DialogoMatricula dialogo = new DialogoMatricula((JFrame) SwingUtilities.getWindowAncestor(this), matriculaController, matriculaSeleccionada, this);
                dialogo.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desactivarActivarMatricula() {
        int filaSeleccionada = tablaMatriculas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor seleccione una matrícula", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMatricula = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean esActivo = ((String) modeloTabla.getValueAt(filaSeleccionada, 7)).contains("✅");
        String accion = esActivo ? "desactivar" : "activar";

        int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de " + accion + " esta matrícula?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                if (esActivo) {
                    matriculaController.desactivarMatricula(idMatricula);
                } else {
                    matriculaController.activarMatricula(idMatricula);
                }
                cargarMatriculas();
                JOptionPane.showMessageDialog(this, "✅ Matrícula " + accion + "da correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
