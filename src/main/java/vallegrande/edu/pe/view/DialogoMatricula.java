package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.MatriculaController;
import vallegrande.edu.pe.model.Curso;
import vallegrande.edu.pe.model.Estudiante;
import vallegrande.edu.pe.model.Matricula;
import vallegrande.edu.pe.utils.Validador;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DialogoMatricula extends JDialog {
    private final MatriculaController matriculaController;
    private final Matricula matricula;
    private final PanelMatricula panelMatricula;
    private JComboBox<EstudianteComboItem> cboEstudiante;
    private JComboBox<CursoComboItem> cboCurso;
    private JTextField txtFechaMatricula, txtCiclo, txtNotaFinal;
    private JComboBox<String> cboEstadoMatricula;
    private JButton btnGuardar, btnCancelar;

    private static final Color COLOR_PRIMARIO = new Color(13, 71, 161);
    private static final Color COLOR_SECUNDARIO = new Color(21, 101, 192);
    private static final Color COLOR_FONDO = new Color(232, 234, 246);
    private static final Color COLOR_TEXTO = new Color(26, 35, 126);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_GRIS = new Color(66, 66, 66);

    public DialogoMatricula(JFrame parent, MatriculaController matriculaController, Matricula matricula, PanelMatricula panelMatricula) {
        super(parent, matricula == null ? "Nueva Matrícula" : "Editar Matrícula", true);
        this.matriculaController = matriculaController;
        this.matricula = matricula;
        this.panelMatricula = panelMatricula;
        inicializarComponentes();
        cargarCombos();
        if (matricula != null) {
            cargarDatos();
        } else {
            txtFechaMatricula.setText(LocalDate.now().toString());
        }
    }

    private void inicializarComponentes() {
        setSize(640, 600);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(matricula == null ? "📝 Nueva Matrícula" : "✏️ Editar Matrícula");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblEstudiante = new JLabel("Estudiante:");
        lblEstudiante.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstudiante.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblEstudiante, gbc);

        cboEstudiante = new JComboBox<>();
        cboEstudiante.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboEstudiante.setPreferredSize(new Dimension(280, 35));
        cboEstudiante.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cboEstudiante, gbc);

        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCurso.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblCurso, gbc);

        cboCurso = new JComboBox<>();
        cboCurso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboCurso.setPreferredSize(new Dimension(280, 35));
        cboCurso.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cboCurso, gbc);

        JLabel lblFechaMatricula = new JLabel("Fecha Matrícula:");
        lblFechaMatricula.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaMatricula.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblFechaMatricula, gbc);

        txtFechaMatricula = new JTextField(22);
        txtFechaMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFechaMatricula.setPreferredSize(new Dimension(280, 35));
        txtFechaMatricula.setEditable(false);
        txtFechaMatricula.setBackground(new Color(220, 220, 220));
        txtFechaMatricula.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtFechaMatricula, gbc);

        JLabel lblCiclo = new JLabel("Ciclo:");
        lblCiclo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCiclo.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblCiclo, gbc);

        txtCiclo = new JTextField(22);
        txtCiclo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCiclo.setPreferredSize(new Dimension(280, 35));
        txtCiclo.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtCiclo, gbc);

        JLabel lblNotaFinal = new JLabel("Nota Final:");
        lblNotaFinal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNotaFinal.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblNotaFinal, gbc);

        txtNotaFinal = new JTextField(22);
        txtNotaFinal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNotaFinal.setPreferredSize(new Dimension(280, 35));
        txtNotaFinal.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtNotaFinal, gbc);

        JLabel lblEstadoMatricula = new JLabel("Estado:");
        lblEstadoMatricula.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstadoMatricula.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblEstadoMatricula, gbc);

        cboEstadoMatricula = new JComboBox<>(new String[]{"activa", "completada", "anulada"});
        cboEstadoMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboEstadoMatricula.setPreferredSize(new Dimension(280, 35));
        cboEstadoMatricula.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cboEstadoMatricula, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setBackground(COLOR_FONDO);

        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGuardar.setBackground(COLOR_PRIMARIO);
        btnGuardar.setForeground(COLOR_BLANCO);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setPreferredSize(new Dimension(160, 45));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardar());

        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCancelar.setBackground(new Color(211, 47, 47));
        btnCancelar.setForeground(COLOR_BLANCO);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setPreferredSize(new Dimension(160, 45));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void cargarCombos() {
        try {
            List<Estudiante> estudiantes = matriculaController.listarEstudiantesActivos();
            for (Estudiante e : estudiantes) {
                cboEstudiante.addItem(new EstudianteComboItem(e));
            }

            List<Curso> cursos = matriculaController.listarCursosActivos();
            for (Curso c : cursos) {
                cboCurso.addItem(new CursoComboItem(c));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {
        for (int i = 0; i < cboEstudiante.getItemCount(); i++) {
            EstudianteComboItem item = cboEstudiante.getItemAt(i);
            if (item.getId() == matricula.getIdEstudiante()) {
                cboEstudiante.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cboCurso.getItemCount(); i++) {
            CursoComboItem item = cboCurso.getItemAt(i);
            if (item.getId() == matricula.getIdCurso()) {
                cboCurso.setSelectedIndex(i);
                break;
            }
        }

        txtFechaMatricula.setText(matricula.getFechaMatricula().toString());
        txtCiclo.setText(matricula.getCiclo());
        if (matricula.getNotaFinal() != null) {
            txtNotaFinal.setText(matricula.getNotaFinal().toString());
        }
        cboEstadoMatricula.setSelectedItem(matricula.getEstadoMatricula());
    }

    private void guardar() {
        EstudianteComboItem estudianteItem = (EstudianteComboItem) cboEstudiante.getSelectedItem();
        CursoComboItem cursoItem = (CursoComboItem) cboCurso.getSelectedItem();
        String ciclo = txtCiclo.getText().trim();
        String notaFinalStr = txtNotaFinal.getText().trim();
        String estadoMatricula = (String) cboEstadoMatricula.getSelectedItem();

        if (estudianteItem == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Debe seleccionar un estudiante", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cursoItem == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Debe seleccionar un curso", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!Validador.validarNoVacio(ciclo)) {
            JOptionPane.showMessageDialog(this, "⚠️ El ciclo es obligatorio", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtCiclo.requestFocus();
            return;
        }

        if (!Validador.validarCiclo(ciclo)) {
            JOptionPane.showMessageDialog(this, "⚠️ Ciclo inválido (formato: YYYY-I o YYYY-II)", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtCiclo.requestFocus();
            return;
        }

        BigDecimal notaFinal = null;
        if (!notaFinalStr.isEmpty()) {
            try {
                notaFinal = new BigDecimal(notaFinalStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "⚠️ Nota final inválida", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtNotaFinal.requestFocus();
                return;
            }

            if (!Validador.validarNota(notaFinal)) {
                JOptionPane.showMessageDialog(this, "⚠️ Nota final debe estar entre 0 y 20", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtNotaFinal.requestFocus();
                return;
            }
        }

        try {
            Integer idExcluir = matricula != null ? matricula.getIdMatricula() : null;
            if (matriculaController.existeMatricula(estudianteItem.getId(), cursoItem.getId(), ciclo, idExcluir)) {
                JOptionPane.showMessageDialog(this, "⚠️ La matrícula ya existe para este estudiante, curso y ciclo", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Matricula matriculaAGuardar;
            if (matricula == null) {
                matriculaAGuardar = new Matricula();
                matriculaAGuardar.setActivo(true);
                matriculaAGuardar.setFechaMatricula(LocalDate.now());
            } else {
                matriculaAGuardar = matricula;
            }

            matriculaAGuardar.setIdEstudiante(estudianteItem.getId());
            matriculaAGuardar.setIdCurso(cursoItem.getId());
            matriculaAGuardar.setCiclo(ciclo);
            matriculaAGuardar.setNotaFinal(notaFinal);
            matriculaAGuardar.setEstadoMatricula(estadoMatricula);

            if (matricula == null) {
                matriculaController.crearMatricula(matriculaAGuardar);
                JOptionPane.showMessageDialog(this, "✅ Matrícula creada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                matriculaController.actualizarMatricula(matriculaAGuardar);
                JOptionPane.showMessageDialog(this, "✅ Matrícula actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            panelMatricula.cargarMatriculas();
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class EstudianteComboItem {
        private final int id;
        private final String nombreCompleto;

        public EstudianteComboItem(Estudiante estudiante) {
            this.id = estudiante.getIdEstudiante();
            this.nombreCompleto = estudiante.getNombres() + " " + estudiante.getApellidos();
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombreCompleto;
        }
    }

    private static class CursoComboItem {
        private final int id;
        private final String nombreCurso;

        public CursoComboItem(Curso curso) {
            this.id = curso.getIdCurso();
            this.nombreCurso = curso.getCodigo() + " - " + curso.getNombre();
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombreCurso;
        }
    }
}
