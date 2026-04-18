package formulario;

import servicio.EstudianteServicio;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 * Instancia el servicio e inicia el formulario en el Event Dispatch Thread (EDT),
 * cumpliendo la buena práctica de Swing para thread-safety.
 */
public class Main {

    public static void main(String[] args) {
        // Establecer apariencia del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, se usa el Look & Feel por defecto de Java
            System.err.println("No se pudo aplicar el Look & Feel del sistema: " + e.getMessage());
        }

        // Lanzar la UI en el Event Dispatch Thread (EDT) — buena práctica Swing
        SwingUtilities.invokeLater(() -> {
            EstudianteServicio servicio = new EstudianteServicio();
            new FormularioEstudiante(servicio);
        });
    }
}
