package formulario;

import modelo.Estudiante;
import servicio.EstudianteServicio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal del sistema de registro de estudiantes.
 *
 * Componentes Swing utilizados:
 *   - JFrame      : ventana principal (mediante herencia)
 *   - JPanel      : organización de secciones del formulario
 *   - JLabel      : etiquetas descriptivas de cada campo
 *   - JTextField  : campos de entrada de texto
 *   - JOptionPane : diálogos de confirmación, error y resultado
 *   - JCheckBox   : selección múltiple de áreas de interés
 *
 * Pilares POO aplicados:
 *   - Herencia        : extiende JFrame
 *   - Encapsulamiento : atributos privados con acceso controlado
 *   - Abstracción     : lógica separada en métodos con nombre claro
 *   - Polimorfismo    : ActionListeners via lambda y @Override de toString()
 */
public class FormularioEstudiante extends JFrame {

    // ── Servicio de lógica de negocio (inyectado) ─────────────────────────────
    private final EstudianteServicio servicio;

    // ── Componentes del formulario (Encapsulamiento: privados) ────────────────
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtCarrera;
    private JTextField txtCodigo;

    private JCheckBox chkProgramacion;
    private JCheckBox chkBaseDatos;
    private JCheckBox chkDisenio;
    private JCheckBox chkRedes;
    private JCheckBox chkSeguridad;
    private JCheckBox chkInteligenciaArtificial;
    private JCheckBox chkTerminos;

    private JLabel lblContador;

    // ── Paleta de colores del sistema ─────────────────────────────────────────
    private static final Color COLOR_PRIMARIO    = new Color(37, 99, 235);
    private static final Color COLOR_PRIMARIO_OSC= new Color(29, 78, 216);
    private static final Color COLOR_FONDO       = new Color(248, 250, 252);
    private static final Color COLOR_BLANCO      = Color.WHITE;
    private static final Color COLOR_TEXTO        = new Color(30, 41, 59);
    private static final Color COLOR_MUTED       = new Color(100, 116, 139);
    private static final Color COLOR_BORDE       = new Color(203, 213, 225);
    private static final Color COLOR_EXITO       = new Color(22, 163, 74);
    private static final Color COLOR_ERROR       = new Color(220, 38, 38);

    // ── Constructor: Herencia de JFrame ───────────────────────────────────────
    public FormularioEstudiante(EstudianteServicio servicio) {
        super("Sistema de Registro de Estudiantes");
        this.servicio = servicio;
        inicializarUI();
        configurarVentana();
    }

    // =========================================================================
    //  ABSTRACCIÓN: construcción de la UI dividida en métodos con nombre claro
    // =========================================================================

    /** Inicializa todos los paneles y los ensambla en el JFrame. */
    private void inicializarUI() {
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout(0, 0));

        add(crearPanelHeader(),    BorderLayout.NORTH);
        add(crearPanelCentral(),   BorderLayout.CENTER);
        add(crearPanelFooter(),    BorderLayout.SOUTH);
    }

    // ── Panel de encabezado ───────────────────────────────────────────────────
    private JPanel crearPanelHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PRIMARIO);
        panel.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel lblTitulo = new JLabel("Registro de Estudiantes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(COLOR_BLANCO);

        JLabel lblSubtitulo = new JLabel("Completa todos los campos para registrar un nuevo estudiante");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(191, 219, 254));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 4));
        textos.setOpaque(false);
        textos.add(lblTitulo);
        textos.add(lblSubtitulo);

        lblContador = new JLabel("Registros: 0");
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblContador.setForeground(new Color(191, 219, 254));
        lblContador.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(textos,      BorderLayout.WEST);
        panel.add(lblContador, BorderLayout.EAST);
        return panel;
    }

    // ── Panel central: agrupa datos personales + intereses + términos ─────────
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(16, 24, 8, 24));

        panel.add(crearSeccionDatosPersonales());
        panel.add(Box.createVerticalStrut(12));
        panel.add(crearSeccionIntereses());
        panel.add(Box.createVerticalStrut(12));
        panel.add(crearSeccionTerminos());
        return panel;
    }

    // ── Sección: datos personales ─────────────────────────────────────────────
    private JPanel crearSeccionDatosPersonales() {
        JPanel contenedor = crearPanelSeccion("Datos Personales");
        JPanel grilla = new JPanel(new GridLayout(0, 2, 12, 10));
        grilla.setBackground(COLOR_BLANCO);

        txtNombre   = crearCampoTexto("Ej: Ana");
        txtApellido = crearCampoTexto("Ej: García Torres");
        txtCorreo   = crearCampoTexto("usuario@correo.com");
        txtCarrera  = crearCampoTexto("Ej: Ingeniería de Sistemas");

        // Código: solo lectura, generado automáticamente
        txtCodigo = crearCampoTexto("");
        txtCodigo.setText(servicio.generarCodigo());
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(241, 245, 249));
        txtCodigo.setForeground(COLOR_MUTED);

        grilla.add(crearEtiqueta("Nombre *"));        grilla.add(txtNombre);
        grilla.add(crearEtiqueta("Apellido *"));       grilla.add(txtApellido);
        grilla.add(crearEtiqueta("Correo electrónico *")); grilla.add(txtCorreo);
        grilla.add(crearEtiqueta("Carrera *"));        grilla.add(txtCarrera);
        grilla.add(crearEtiqueta("Código (auto)"));    grilla.add(txtCodigo);

        contenedor.add(grilla, BorderLayout.CENTER);
        return contenedor;
    }

    // ── Sección: áreas de interés ─────────────────────────────────────────────
    private JPanel crearSeccionIntereses() {
        JPanel contenedor = crearPanelSeccion("Áreas de Interés (selecciona al menos una)");
        JPanel grilla = new JPanel(new GridLayout(2, 3, 10, 6));
        grilla.setBackground(COLOR_BLANCO);

        chkProgramacion         = crearCheckBox("Programación");
        chkBaseDatos            = crearCheckBox("Bases de Datos");
        chkDisenio              = crearCheckBox("Diseño UI/UX");
        chkRedes                = crearCheckBox("Redes");
        chkSeguridad            = crearCheckBox("Seguridad");
        chkInteligenciaArtificial = crearCheckBox("Inteligencia Artificial");

        grilla.add(chkProgramacion);
        grilla.add(chkBaseDatos);
        grilla.add(chkDisenio);
        grilla.add(chkRedes);
        grilla.add(chkSeguridad);
        grilla.add(chkInteligenciaArtificial);

        contenedor.add(grilla, BorderLayout.CENTER);
        return contenedor;
    }

    // ── Sección: términos y condiciones ───────────────────────────────────────
    private JPanel crearSeccionTerminos() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(COLOR_FONDO);

        chkTerminos = new JCheckBox(
            "  Acepto los términos y condiciones del sistema de registro");
        chkTerminos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkTerminos.setBackground(COLOR_FONDO);
        chkTerminos.setForeground(COLOR_TEXTO);

        panel.add(chkTerminos);
        return panel;
    }

    // ── Panel de botones (footer) ─────────────────────────────────────────────
    private JPanel crearPanelFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 14));
        panel.setBackground(new Color(241, 245, 249));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDE));

        JButton btnCancelar  = crearBotonSecundario("Cancelar");
        JButton btnLimpiar   = crearBotonSecundario("Limpiar");
        JButton btnRegistrar = crearBotonPrimario("Registrar Estudiante");

        // Polimorfismo: lambdas implementan ActionListener.actionPerformed()
        btnCancelar .addActionListener(e -> confirmarSalida());
        btnLimpiar  .addActionListener(e -> limpiarFormulario());
        btnRegistrar.addActionListener(e -> procesarRegistro());

        panel.add(btnCancelar);
        panel.add(btnLimpiar);
        panel.add(btnRegistrar);
        return panel;
    }

    // =========================================================================
    //  LÓGICA DEL FORMULARIO
    // =========================================================================

    /** Recopila datos del formulario, delega al servicio y muestra resultado. */
    private void procesarRegistro() {
        if (!chkTerminos.isSelected()) {
            mostrarAdvertencia("Debes aceptar los términos y condiciones para continuar.");
            return;
        }

        List<String> intereses = obtenerInteresesSeleccionados();

        try {
            Estudiante nuevo = servicio.registrarEstudiante(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtCorreo.getText(),
                    txtCarrera.getText(),
                    intereses
            );
            // Mostrar resumen con JOptionPane
            JOptionPane.showMessageDialog(
                    this,
                    servicio.generarResumen(nuevo),
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            actualizarContador();
            limpiarFormulario();

        } catch (IllegalArgumentException ex) {
            // Mostrar error de validación con JOptionPane
            mostrarError(ex.getMessage());
        }
    }

    /** Devuelve la lista de intereses marcados con JCheckBox. */
    private List<String> obtenerInteresesSeleccionados() {
        List<String> seleccionados = new ArrayList<>();
        if (chkProgramacion.isSelected())          seleccionados.add("Programación");
        if (chkBaseDatos.isSelected())             seleccionados.add("Bases de Datos");
        if (chkDisenio.isSelected())               seleccionados.add("Diseño UI/UX");
        if (chkRedes.isSelected())                 seleccionados.add("Redes");
        if (chkSeguridad.isSelected())             seleccionados.add("Seguridad");
        if (chkInteligenciaArtificial.isSelected())seleccionados.add("Inteligencia Artificial");
        return seleccionados;
    }

    /** Limpia todos los campos y checkboxes del formulario. */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtCarrera.setText("");
        txtCodigo.setText(servicio.generarCodigo());

        for (JCheckBox cb : new JCheckBox[]{
                chkProgramacion, chkBaseDatos, chkDisenio,
                chkRedes, chkSeguridad, chkInteligenciaArtificial, chkTerminos}) {
            cb.setSelected(false);
        }
        txtNombre.requestFocus();
    }

    /** Muestra un diálogo de confirmación antes de cerrar la aplicación. */
    private void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas salir del sistema?\nRegistros guardados en esta sesión: "
                        + servicio.totalRegistros(),
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /** Actualiza el contador de registros en el header. */
    private void actualizarContador() {
        lblContador.setText("Registros: " + servicio.totalRegistros());
    }

    // =========================================================================
    //  MÉTODOS AUXILIARES DE UI (fábrica de componentes)
    // =========================================================================

    /** Crea un panel con borde titulado para agrupar secciones. */
    private JPanel crearPanelSeccion(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BLANCO);
        TitledBorder borde = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                "  " + titulo + "  "
        );
        borde.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        borde.setTitleColor(COLOR_PRIMARIO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                borde,
                new EmptyBorder(8, 10, 10, 10)
        ));
        return panel;
    }

    /** Crea un JLabel con el estilo estándar del formulario. */
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(COLOR_TEXTO);
        return lbl;
    }

    /** Crea un JTextField con el estilo estándar del formulario. */
    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        campo.setPreferredSize(new Dimension(0, 34));
        // Mostrar placeholder como texto inicial en gris claro
        if (!placeholder.isEmpty()) {
            campo.setText(placeholder);
            campo.setForeground(new Color(148, 163, 184));
            campo.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (campo.getText().equals(placeholder)) {
                        campo.setText("");
                        campo.setForeground(COLOR_TEXTO);
                    }
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (campo.getText().isEmpty()) {
                        campo.setText(placeholder);
                        campo.setForeground(new Color(148, 163, 184));
                    }
                }
            });
        }
        return campo;
    }

    /** Crea un JCheckBox con el estilo estándar del formulario. */
    private JCheckBox crearCheckBox(String texto) {
        JCheckBox cb = new JCheckBox(texto);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBackground(COLOR_BLANCO);
        cb.setForeground(COLOR_TEXTO);
        return cb;
    }

    /** Crea un botón primario (acción principal). */
    private JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(COLOR_BLANCO);
        btn.setBorder(new EmptyBorder(9, 20, 9, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_PRIMARIO_OSC);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_PRIMARIO);
            }
        });
        return btn;
    }

    /** Crea un botón secundario (acciones opcionales). */
    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(COLOR_BLANCO);
        btn.setForeground(COLOR_TEXTO);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /** Muestra un diálogo de advertencia con JOptionPane. */
    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    /** Muestra un diálogo de error con JOptionPane. */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación",
                JOptionPane.ERROR_MESSAGE);
    }

    // ── Configuración final de la ventana ─────────────────────────────────────
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSalida();
            }
        });
        setSize(660, 580);
        setMinimumSize(new Dimension(560, 520));
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
