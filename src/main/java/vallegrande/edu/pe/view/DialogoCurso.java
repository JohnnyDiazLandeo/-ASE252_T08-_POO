package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.CursoController;
import vallegrande.edu.pe.model.Curso;
import vallegrande.edu.pe.utils.Validador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class DialogoCurso extends JDialog {
    private final CursoController cursoController;
    private final Curso curso;
    private final PanelCurso panelCurso;
    private JTextField txtCodigo, txtNombre, txtCreditos, txtHorasSemana, txtFechaRegistro;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnCancelar;

    private static final Color COLOR_PRIMARIO = new Color(10, 60, 120);
    private static final Color COLOR_SECUNDARIO = new Color(25, 90, 160);
    private static final Color COLOR_FONDO = new Color(30, 50, 80);
    private static final Color COLOR_FONDO_CLARO = new Color(40, 70, 110);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_TEXTO_OSCURO = new Color(200, 220, 255);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_GRIS = new Color(120, 120, 120);

    public DialogoCurso(JFrame parent, CursoController cursoController, Curso curso, PanelCurso panelCurso) {
        super(parent, curso == null ? "Nuevo Curso" : "Editar Curso", true);
        this.cursoController = cursoController;
        this.curso = curso;
        this.panelCurso = panelCurso;
        inicializarComponentes();
        if (curso != null) {
            cargarDatos();
        } else {
            txtFechaRegistro.setText(LocalDate.now().toString());
        }
    }

    private void inicializarComponentes() {
        setSize(620, 600);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(curso == null ? "📚 Nuevo Curso" : "✏️ Editar Curso");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblCodigo.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblCodigo, gbc);

        txtCodigo = new JTextField(24);
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtCodigo.setPreferredSize(new Dimension(260, 40));
        txtCodigo.setBackground(COLOR_FONDO_CLARO);
        txtCodigo.setForeground(COLOR_TEXTO);
        txtCodigo.setCaretColor(COLOR_TEXTO);
        txtCodigo.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtCodigo, gbc);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNombre.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField(24);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNombre.setPreferredSize(new Dimension(260, 40));
        txtNombre.setBackground(COLOR_FONDO_CLARO);
        txtNombre.setForeground(COLOR_TEXTO);
        txtNombre.setCaretColor(COLOR_TEXTO);
        txtNombre.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtNombre, gbc);

        JLabel lblCreditos = new JLabel("Créditos:");
        lblCreditos.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblCreditos.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblCreditos, gbc);

        txtCreditos = new JTextField(24);
        txtCreditos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtCreditos.setPreferredSize(new Dimension(260, 40));
        txtCreditos.setBackground(COLOR_FONDO_CLARO);
        txtCreditos.setForeground(COLOR_TEXTO);
        txtCreditos.setCaretColor(COLOR_TEXTO);
        txtCreditos.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtCreditos, gbc);

        JLabel lblHorasSemana = new JLabel("Horas/Semana:");
        lblHorasSemana.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblHorasSemana.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblHorasSemana, gbc);

        txtHorasSemana = new JTextField(24);
        txtHorasSemana.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtHorasSemana.setPreferredSize(new Dimension(260, 40));
        txtHorasSemana.setBackground(COLOR_FONDO_CLARO);
        txtHorasSemana.setForeground(COLOR_TEXTO);
        txtHorasSemana.setCaretColor(COLOR_TEXTO);
        txtHorasSemana.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtHorasSemana, gbc);

        JLabel lblFechaRegistro = new JLabel("Fecha Registro:");
        lblFechaRegistro.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFechaRegistro.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblFechaRegistro, gbc);

        txtFechaRegistro = new JTextField(24);
        txtFechaRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtFechaRegistro.setPreferredSize(new Dimension(260, 40));
        txtFechaRegistro.setEditable(false);
        txtFechaRegistro.setBackground(COLOR_GRIS);
        txtFechaRegistro.setForeground(COLOR_TEXTO);
        txtFechaRegistro.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtFechaRegistro, gbc);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblDescripcion.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(lblDescripcion, gbc);

        txtDescripcion = new JTextArea(4, 24);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBackground(COLOR_FONDO_CLARO);
        txtDescripcion.setForeground(COLOR_TEXTO);
        txtDescripcion.setCaretColor(COLOR_TEXTO);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(scrollDescripcion, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 25));
        panelBotones.setBackground(COLOR_FONDO);

        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGuardar.setBackground(COLOR_SECUNDARIO);
        btnGuardar.setForeground(COLOR_TEXTO);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setPreferredSize(new Dimension(180, 50));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardar());

        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancelar.setBackground(new Color(198, 40, 40));
        btnCancelar.setForeground(COLOR_TEXTO);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setPreferredSize(new Dimension(180, 50));
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

    private void cargarDatos() {
        txtCodigo.setText(curso.getCodigo());
        txtNombre.setText(curso.getNombre());
        txtCreditos.setText(String.valueOf(curso.getCreditos()));
        txtHorasSemana.setText(String.valueOf(curso.getHorasSemana()));
        txtFechaRegistro.setText(curso.getFechaRegistro().toString());
        txtDescripcion.setText(curso.getDescripcion());
    }

    private void guardar() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String creditosStr = txtCreditos.getText().trim();
        String horasStr = txtHorasSemana.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (!Validador.validarNoVacio(codigo)) {
            JOptionPane.showMessageDialog(this, "⚠️ El código es obligatorio", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }

        if (!Validador.validarCodigoCurso(codigo)) {
            JOptionPane.showMessageDialog(this, "⚠️ Código inválido (debe ser 3-10 caracteres alfanuméricos)", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }

        if (!Validador.validarNoVacio(nombre)) {
            JOptionPane.showMessageDialog(this, "⚠️ El nombre es obligatorio", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }

        int creditos;
        try {
            creditos = Integer.parseInt(creditosStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Créditos debe ser un número", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtCreditos.requestFocus();
            return;
        }

        if (!Validador.validarCredito(creditos)) {
            JOptionPane.showMessageDialog(this, "⚠️ Créditos debe estar entre 1 y 10", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtCreditos.requestFocus();
            return;
        }

        int horas;
        try {
            horas = Integer.parseInt(horasStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Horas/Semana debe ser un número", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtHorasSemana.requestFocus();
            return;
        }

        if (!Validador.validarHoras(horas)) {
            JOptionPane.showMessageDialog(this, "⚠️ Horas/Semana debe estar entre 1 y 40", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtHorasSemana.requestFocus();
            return;
        }

        try {
            Integer idExcluir = curso != null ? curso.getIdCurso() : null;
            if (cursoController.existeCodigoCurso(codigo, idExcluir)) {
                JOptionPane.showMessageDialog(this, "⚠️ El código ya existe", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtCodigo.requestFocus();
                return;
            }

            Curso cursoAGuardar;
            if (curso == null) {
                cursoAGuardar = new Curso();
                cursoAGuardar.setActivo(true);
            } else {
                cursoAGuardar = curso;
            }

            cursoAGuardar.setCodigo(codigo);
            cursoAGuardar.setNombre(nombre);
            cursoAGuardar.setCreditos(creditos);
            cursoAGuardar.setHorasSemana(horas);
            cursoAGuardar.setDescripcion(descripcion);

            if (curso == null) {
                cursoController.crearCurso(cursoAGuardar);
                JOptionPane.showMessageDialog(this, "✅ Curso creado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                cursoController.actualizarCurso(cursoAGuardar);
                JOptionPane.showMessageDialog(this, "✅ Curso actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            panelCurso.cargarCursos();
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
