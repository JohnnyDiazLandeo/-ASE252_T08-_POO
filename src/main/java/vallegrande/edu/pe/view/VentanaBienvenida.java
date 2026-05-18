package vallegrande.edu.pe.view;

import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaBienvenida extends JFrame {
    private final Usuario usuario;
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuGestion;
    private JMenuItem itemCerrarSesion, itemSalir, itemCursos, itemEstudiantes, itemMatriculas;
    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    private static final Color COLOR_PRIMARIO = new Color(10, 60, 120);
    private static final Color COLOR_SECUNDARIO = new Color(25, 90, 160);
    private static final Color COLOR_FONDO = new Color(30, 50, 80);
    private static final Color COLOR_FONDO_CLARO = new Color(40, 70, 110);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_TEXTO_OSCURO = new Color(200, 220, 255);
    private static final Color COLOR_BLANCO = Color.WHITE;

    public VentanaBienvenida(Usuario usuario) {
        this.usuario = usuario;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("🎓 Sistema de Gestión de Cursos y Matrículas - Valle Grande");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1350, 900);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);

        crearMenu();
        crearPanelPrincipal();

        setJMenuBar(menuBar);
        add(panelPrincipal);
    }

    private void crearMenu() {
        menuBar = new JMenuBar();
        menuBar.setBackground(COLOR_PRIMARIO);
        menuBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        menuArchivo = new JMenu("📁 Archivo");
        menuArchivo.setForeground(COLOR_TEXTO);
        menuArchivo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        itemCerrarSesion = new JMenuItem("🔒 Cerrar Sesión");
        itemCerrarSesion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        itemSalir = new JMenuItem("❌ Salir");
        itemSalir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemCerrarSesion);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuGestion = new JMenu("📊 Gestión");
        menuGestion.setForeground(COLOR_TEXTO);
        menuGestion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        itemCursos = new JMenuItem("📚 Cursos");
        itemCursos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemCursos.addActionListener(e -> mostrarPanel("cursos"));
        itemEstudiantes = new JMenuItem("👨‍🎓 Estudiantes");
        itemEstudiantes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemEstudiantes.addActionListener(e -> mostrarPanel("estudiantes"));
        itemMatriculas = new JMenuItem("📝 Matrículas");
        itemMatriculas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemMatriculas.addActionListener(e -> mostrarPanel("matriculas"));
        menuGestion.add(itemCursos);
        menuGestion.add(itemEstudiantes);
        menuGestion.add(itemMatriculas);

        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
    }

    private void crearPanelPrincipal() {
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(COLOR_FONDO);

        JPanel panelBienvenida = new JPanel(new BorderLayout(30, 30));
        panelBienvenida.setBackground(COLOR_FONDO);
        panelBienvenida.setBorder(BorderFactory.createEmptyBorder(70, 70, 70, 70));

        ImageIcon logoIcon = null;
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource("images/vallegrande_captura.jpg");
            if (imgURL != null) {
                logoIcon = new ImageIcon(imgURL);
                Image scaledImage = logoIcon.getImage().getScaledInstance(450, -1, Image.SCALE_SMOOTH);
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
            lblLogo.setText("🎓 Valle Grande");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 50));
            lblLogo.setForeground(COLOR_TEXTO_OSCURO);
        }

        JPanel panelTexto = new JPanel(new GridLayout(0, 1, 20, 20));
        panelTexto.setBackground(COLOR_FONDO);

        JLabel lblBienvenida = new JLabel("¡Bienvenido, " + usuario.getNombreUsuario() + "!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblBienvenida.setForeground(COLOR_TEXTO_OSCURO);

        JLabel lblInstitucion = new JLabel("Instituto de Educación Superior Valle Grande", SwingConstants.CENTER);
        lblInstitucion.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblInstitucion.setForeground(new Color(180, 200, 230));

        JLabel lblDescripcion = new JLabel("<html><center>Sistema de gestión académico para la administración de cursos, estudiantes y matrículas.<br><br>🎯 Seleccione una opción del menú 'Gestión' arriba para comenzar.</center></html>", SwingConstants.CENTER);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblDescripcion.setForeground(new Color(160, 190, 220));

        panelTexto.add(lblBienvenida);
        panelTexto.add(lblInstitucion);
        panelTexto.add(Box.createVerticalStrut(40));
        panelTexto.add(lblDescripcion);

        panelBienvenida.add(lblLogo, BorderLayout.NORTH);
        panelBienvenida.add(panelTexto, BorderLayout.CENTER);

        PanelCurso panelCurso = new PanelCurso();
        PanelEstudiante panelEstudiante = new PanelEstudiante();
        PanelMatricula panelMatricula = new PanelMatricula();

        panelPrincipal.add(panelBienvenida, "bienvenida");
        panelPrincipal.add(panelCurso, "cursos");
        panelPrincipal.add(panelEstudiante, "estudiantes");
        panelPrincipal.add(panelMatricula, "matriculas");
    }

    private void mostrarPanel(String nombre) {
        cardLayout.show(panelPrincipal, nombre);
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            this.dispose();
        }
    }
}
