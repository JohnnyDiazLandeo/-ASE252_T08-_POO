package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.EstudianteController;
import vallegrande.edu.pe.model.Estudiante;
import vallegrande.edu.pe.utils.Validador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DialogoEstudiante extends JDialog {
    private final EstudianteController estudianteController;
    private final Estudiante estudiante;
    private final PanelEstudiante panelEstudiante;
    private JComboBox<String> cboTipoDocumento;
    private JTextField txtNumeroDocumento, txtNombres, txtApellidos, txtEmail, txtTelefono, txtDireccion;
    private JSpinner spinnerFechaNacimiento;
    private JComboBox<String> cboGenero;
    private JButton btnGuardar, btnCancelar;

    private static final Color COLOR_PRIMARIO = new Color(13, 71, 161);
    private static final Color COLOR_SECUNDARIO = new Color(21, 101, 192);
    private static final Color COLOR_FONDO = new Color(232, 234, 246);
    private static final Color COLOR_TEXTO = new Color(26, 35, 126);
    private static final Color COLOR_BLANCO = Color.WHITE;
    private static final Color COLOR_GRIS = new Color(66, 66, 66);

    public DialogoEstudiante(JFrame parent, EstudianteController estudianteController, Estudiante estudiante, PanelEstudiante panelEstudiante) {
        super(parent, estudiante == null ? "Nuevo Estudiante" : "Editar Estudiante", true);
        this.estudianteController = estudianteController;
        this.estudiante = estudiante;
        this.panelEstudiante = panelEstudiante;
        inicializarComponentes();
        if (estudiante != null) {
            cargarDatos();
        }
    }

    private void inicializarComponentes() {
        setSize(620, 680);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(estudiante == null ? "📋 Nuevo Estudiante" : "✏️ Editar Estudiante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblTipoDocumento = new JLabel("Tipo Documento:");
        lblTipoDocumento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTipoDocumento.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblTipoDocumento, gbc);

        cboTipoDocumento = new JComboBox<>(new String[]{"DNI", "CE", "Pasaporte"});
        cboTipoDocumento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboTipoDocumento.setPreferredSize(new Dimension(230, 35));
        cboTipoDocumento.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cboTipoDocumento, gbc);

        JLabel lblNumeroDocumento = new JLabel("Número Documento:");
        lblNumeroDocumento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNumeroDocumento.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblNumeroDocumento, gbc);

        txtNumeroDocumento = new JTextField(22);
        txtNumeroDocumento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNumeroDocumento.setPreferredSize(new Dimension(230, 35));
        txtNumeroDocumento.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtNumeroDocumento, gbc);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombres.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblNombres, gbc);

        txtNombres = new JTextField(22);
        txtNombres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombres.setPreferredSize(new Dimension(230, 35));
        txtNombres.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtNombres, gbc);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblApellidos.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblApellidos, gbc);

        txtApellidos = new JTextField(22);
        txtApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellidos.setPreferredSize(new Dimension(230, 35));
        txtApellidos.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtApellidos, gbc);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmail.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField(22);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(230, 35));
        txtEmail.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtEmail, gbc);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTelefono.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblTelefono, gbc);

        txtTelefono = new JTextField(22);
        txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTelefono.setPreferredSize(new Dimension(230, 35));
        txtTelefono.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtTelefono, gbc);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDireccion.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblDireccion, gbc);

        txtDireccion = new JTextField(22);
        txtDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDireccion.setPreferredSize(new Dimension(230, 35));
        txtDireccion.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtDireccion, gbc);

        JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
        lblFechaNacimiento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaNacimiento.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblFechaNacimiento, gbc);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerFechaNacimiento = new JSpinner(dateModel);
        spinnerFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerFechaNacimiento.setEditor(new JSpinner.DateEditor(spinnerFechaNacimiento, "yyyy-MM-dd"));
        spinnerFechaNacimiento.setPreferredSize(new Dimension(230, 35));
        spinnerFechaNacimiento.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(spinnerFechaNacimiento, gbc);

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGenero.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblGenero, gbc);

        cboGenero = new JComboBox<>(new String[]{"M", "F"});
        cboGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboGenero.setPreferredSize(new Dimension(230, 35));
        cboGenero.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cboGenero, gbc);

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
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void cargarDatos() {
        cboTipoDocumento.setSelectedItem(estudiante.getTipoDocumento());
        txtNumeroDocumento.setText(estudiante.getNumeroDocumento());
        txtNombres.setText(estudiante.getNombres());
        txtApellidos.setText(estudiante.getApellidos());
        txtEmail.setText(estudiante.getEmail());
        txtTelefono.setText(estudiante.getTelefono());
        txtDireccion.setText(estudiante.getDireccion());
        if (estudiante.getFechaNacimiento() != null) {
            spinnerFechaNacimiento.setValue(Date.from(estudiante.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        cboGenero.setSelectedItem(estudiante.getGenero());
    }

    private void guardar() {
        String tipoDocumento = (String) cboTipoDocumento.getSelectedItem();
        String numeroDocumento = txtNumeroDocumento.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        Date fechaNacimientoDate = (Date) spinnerFechaNacimiento.getValue();
        LocalDate fechaNacimiento = fechaNacimientoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String genero = (String) cboGenero.getSelectedItem();

        if (!Validador.validarNumeroDocumento(tipoDocumento, numeroDocumento)) {
            String mensaje;
            switch (tipoDocumento) {
                case "DNI":
                    mensaje = "⚠️ DNI inválido (debe ser 8 dígitos)";
                    break;
                case "CE":
                    mensaje = "⚠️ CE inválido (debe ser 9 dígitos)";
                    break;
                case "Pasaporte":
                    mensaje = "⚠️ Pasaporte inválido (debe ser 5-20 caracteres)";
                    break;
                default:
                    mensaje = "⚠️ Número de documento inválido";
            }
            JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
            txtNumeroDocumento.requestFocus();
            return;
        }

        if (!Validador.validarNoVacio(nombres)) {
            JOptionPane.showMessageDialog(this, "⚠️ Los nombres son obligatorios", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtNombres.requestFocus();
            return;
        }

        if (!Validador.validarNoVacio(apellidos)) {
            JOptionPane.showMessageDialog(this, "⚠️ Los apellidos son obligatorios", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtApellidos.requestFocus();
            return;
        }

        if (!Validador.validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "⚠️ Email inválido", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        if (!Validador.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this, "⚠️ Teléfono inválido (debe ser 7-15 dígitos)", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return;
        }

        try {
            Integer idExcluir = estudiante != null ? estudiante.getIdEstudiante() : null;
            if (estudianteController.existeNumeroDocumento(numeroDocumento, idExcluir)) {
                JOptionPane.showMessageDialog(this, "⚠️ El número de documento ya existe", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtNumeroDocumento.requestFocus();
                return;
            }

            Estudiante estudianteAGuardar;
            if (estudiante == null) {
                estudianteAGuardar = new Estudiante();
                estudianteAGuardar.setActivo(true);
            } else {
                estudianteAGuardar = estudiante;
            }

            estudianteAGuardar.setTipoDocumento(tipoDocumento);
            estudianteAGuardar.setNumeroDocumento(numeroDocumento);
            estudianteAGuardar.setNombres(nombres);
            estudianteAGuardar.setApellidos(apellidos);
            estudianteAGuardar.setEmail(email);
            estudianteAGuardar.setTelefono(telefono.isEmpty() ? null : telefono);
            estudianteAGuardar.setDireccion(direccion.isEmpty() ? null : direccion);
            estudianteAGuardar.setFechaNacimiento(fechaNacimiento);
            estudianteAGuardar.setGenero(genero);

            if (estudiante == null) {
                estudianteController.crearEstudiante(estudianteAGuardar);
                JOptionPane.showMessageDialog(this, "✅ Estudiante creado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                estudianteController.actualizarEstudiante(estudianteAGuardar);
                JOptionPane.showMessageDialog(this, "✅ Estudiante actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            panelEstudiante.cargarEstudiantes();
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
