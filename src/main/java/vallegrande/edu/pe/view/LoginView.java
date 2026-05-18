package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.LoginController;
import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginView extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private final LoginController loginController;

    private static final Color COLOR_PRIMARIO = new Color(10, 60, 120);
    private static final Color COLOR_SECUNDARIO = new Color(25, 90, 160);
    private static final Color COLOR_FONDO = new Color(30, 50, 80);
    private static final Color COLOR_FONDO_CLARO = new Color(40, 70, 110);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_TEXTO_OSCURO = new Color(200, 220, 255);
    private static final Color COLOR_BLANCO = Color.WHITE;

    public LoginView() {
        this.loginController = new LoginController();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("🔐 Iniciar Sesión - Valle Grande");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(540, 540);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(45, 45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon logoIcon = null;
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/vallegrande_captura.jpg");
            if (imgURL != null) {
                logoIcon = new ImageIcon(imgURL);
                Image scaledImage = logoIcon.getImage().getScaledInstance(220, -1, Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        if (logoIcon != null) {
            lblLogo.setIcon(logoIcon);
        } else {
            lblLogo.setText("🎓");
            lblLogo.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblLogo, gbc);

        JLabel lblTitulo = new JLabel("Sistema de Gestión Académica");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_OSCURO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Instituto Valle Grande");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(180, 200, 230));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblSubtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lblUsuario = new JLabel("👤 Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblUsuario.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblUsuario, gbc);

        txtUsuario = new JTextField(22);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setPreferredSize(new Dimension(240, 40));
        txtUsuario.setBackground(COLOR_FONDO_CLARO);
        txtUsuario.setForeground(COLOR_TEXTO);
        txtUsuario.setCaretColor(COLOR_TEXTO);
        txtUsuario.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 2));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtUsuario, gbc);

        JLabel lblContrasena = new JLabel("🔒 Contraseña:");
        lblContrasena.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblContrasena.setForeground(COLOR_TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblContrasena, gbc);

        txtContrasena = new JPasswordField(22);
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtContrasena.setPreferredSize(new Dimension(240, 40));
        txtContrasena.setBackground(COLOR_FONDO_CLARO);
        txtContrasena.setForeground(COLOR_TEXTO);
        txtContrasena.setCaretColor(COLOR_TEXTO);
        txtContrasena.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 2));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtContrasena, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setBackground(COLOR_FONDO);

        btnIniciarSesion = new JButton("🚀 Iniciar Sesión");
        btnIniciarSesion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnIniciarSesion.setBackground(COLOR_SECUNDARIO);
        btnIniciarSesion.setForeground(COLOR_BLANCO);
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setBorderPainted(false);
        btnIniciarSesion.setPreferredSize(new Dimension(250, 50));
        btnIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.addActionListener(e -> iniciarSesion());

        panelBotones.add(btnIniciarSesion);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);

        getRootPane().setDefaultButton(btnIniciarSesion);

        add(panel);
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Por favor complete todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuarioValidado = loginController.iniciarSesion(usuario, contrasena);
            if (usuarioValidado != null) {
                JOptionPane.showMessageDialog(this, "✅ Bienvenido " + usuarioValidado.getNombreUsuario(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                VentanaBienvenida ventana = new VentanaBienvenida(usuarioValidado);
                ventana.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
