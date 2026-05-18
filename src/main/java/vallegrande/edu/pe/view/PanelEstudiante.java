package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.EstudianteController;
import vallegrande.edu.pe.model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelEstudiante extends JPanel {
    private final EstudianteController estudianteController;
    private JTable tablaEstudiantes;
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

    public PanelEstudiante() {
        this.estudianteController = new EstudianteController();
        inicializarComponentes();
        cargarEstudiantes();
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
        btnBuscar.addActionListener(e -> buscarEstudiantes());
        panelBusqueda.add(btnBuscar);
        chkMostrarInactivos = new JCheckBox("Mostrar inactivos");
        chkMostrarInactivos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkMostrarInactivos.setBackground(COLOR_FONDO);
        chkMostrarInactivos.setForeground(COLOR_TEXTO);
        chkMostrarInactivos.addActionListener(e -> cargarEstudiantes());
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
        btnNuevo.addActionListener(e -> nuevoEstudiante());
        btnEditar = new JButton("✏️ Editar");
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEditar.setBackground(new Color(46, 125, 50));
        btnEditar.setForeground(COLOR_TEXTO);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setPreferredSize(new Dimension(130, 35));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.addActionListener(e -> editarEstudiante());
        btnDesactivarActivar = new JButton("🔄 Desactivar/Activar");
        btnDesactivarActivar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDesactivarActivar.setBackground(new Color(245, 124, 0));
        btnDesactivarActivar.setForeground(COLOR_TEXTO);
        btnDesactivarActivar.setFocusPainted(false);
        btnDesactivarActivar.setBorderPainted(false);
        btnDesactivarActivar.setPreferredSize(new Dimension(200, 35));
        btnDesactivarActivar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDesactivarActivar.addActionListener(e -> desactivarActivarEstudiante());
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnDesactivarActivar);

        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        panelSuperior.add(panelBotones, BorderLayout.EAST);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Tipo Doc.", "Número Doc.", "Nombres", "Apellidos", "Email", "Teléfono", "Activo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEstudiantes.setRowHeight(35);
        tablaEstudiantes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaEstudiantes.setBackground(COLOR_FONDO_CLARO);
        tablaEstudiantes.setForeground(COLOR_TEXTO);
        tablaEstudiantes.setGridColor(new Color(60, 90, 130));
        tablaEstudiantes.setSelectionBackground(COLOR_SECUNDARIO);
        tablaEstudiantes.setSelectionForeground(COLOR_TEXTO);
        JTableHeader header = tablaEstudiantes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(COLOR_TEXTO);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        tablaEstudiantes.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarEstudiantes() {
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Estudiante> estudiantes = estudianteController.listarEstudiantes(incluirInactivos);
            actualizarTabla(estudiantes);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al cargar estudiantes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEstudiantes() {
        String texto = txtBuscar.getText().trim();
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Estudiante> estudiantes;
            if (texto.isEmpty()) {
                estudiantes = estudianteController.listarEstudiantes(incluirInactivos);
            } else {
                estudiantes = estudianteController.buscarEstudiantes(texto, incluirInactivos);
            }
            actualizarTabla(estudiantes);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al buscar estudiantes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(List<Estudiante> estudiantes) {
        modeloTabla.setRowCount(0);
        for (Estudiante estudiante : estudiantes) {
            modeloTabla.addRow(new Object[]{
                    estudiante.getIdEstudiante(),
                    estudiante.getTipoDocumento(),
                    estudiante.getNumeroDocumento(),
                    estudiante.getNombres(),
                    estudiante.getApellidos(),
                    estudiante.getEmail(),
                    estudiante.getTelefono(),
                    estudiante.isActivo() ? "✅ Sí" : "❌ No"
            });
        }
    }

    private void nuevoEstudiante() {
        DialogoEstudiante dialogo = new DialogoEstudiante((JFrame) SwingUtilities.getWindowAncestor(this), estudianteController, null, this);
        dialogo.setVisible(true);
    }

    private void editarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor seleccione un estudiante", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        try {
            boolean incluirInactivos = chkMostrarInactivos.isSelected();
            List<Estudiante> estudiantes = estudianteController.listarEstudiantes(incluirInactivos);
            Estudiante estudianteSeleccionado = estudiantes.stream()
                    .filter(e -> e.getIdEstudiante() == idEstudiante)
                    .findFirst()
                    .orElse(null);

            if (estudianteSeleccionado != null) {
                DialogoEstudiante dialogo = new DialogoEstudiante((JFrame) SwingUtilities.getWindowAncestor(this), estudianteController, estudianteSeleccionado, this);
                dialogo.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desactivarActivarEstudiante() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor seleccione un estudiante", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean esActivo = ((String) modeloTabla.getValueAt(filaSeleccionada, 7)).contains("✅");
        String accion = esActivo ? "desactivar" : "activar";

        int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de " + accion + " este estudiante?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                if (esActivo) {
                    estudianteController.desactivarEstudiante(idEstudiante);
                } else {
                    estudianteController.activarEstudiante(idEstudiante);
                }
                cargarEstudiantes();
                JOptionPane.showMessageDialog(this, "✅ Estudiante " + accion + "do correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
