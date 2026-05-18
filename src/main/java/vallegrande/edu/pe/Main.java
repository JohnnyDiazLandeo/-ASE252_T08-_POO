package vallegrande.edu.pe;

import vallegrande.edu.pe.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
