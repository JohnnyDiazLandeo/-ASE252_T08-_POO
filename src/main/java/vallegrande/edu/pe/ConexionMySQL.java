package vallegrande.edu.pe;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionMySQL {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream inputStream = ConexionMySQL.class.getClassLoader().getResourceAsStream("config.properties");
            if (inputStream == null) {
                throw new IOException("Archivo config.properties no encontrado en el classpath");
            }
            properties.load(inputStream);
            Class.forName(properties.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Error al cargar la configuración de la base de datos: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
