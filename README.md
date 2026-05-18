# Sistema de Gestión de Cursos y Matrículas

## Equipo 08
- Integrante 1: Brando Jemith FLores Lucana 
- Integrante 2: Jhony Diaz Landeo


## Descripción del Proyecto
Sistema de gestión académico desarrollado en Java con Swing para la gestión de cursos y estudiantes del Valle Grande. Implementa el patrón MVC y utiliza MySQL como base de datos.

## Requisitos Técnicos
- **JDK**: Versión 25
- **Maven**: Para la gestión de dependencias
- **MySQL**: Base de datos (ya tienes el script SQL)
- **IDE**: Recomendado IntelliJ IDEA o Eclipse

## Credenciales de Acceso
- **Usuario**: admin
- **Contraseña**: admin123

## Configuración de la Base de Datos
- **Nombre de la base de datos**: vallegrande_db_POO
- **Usuario**: root
- **Contraseña**: 981837328
- **URL**: jdbc:mysql://localhost:3306/vallegrande_db_POO?useSSL=false&serverTimezone=UTC

## Cómo Ejecutar el Proyecto

1. **Clonar o descargar el proyecto**
2. **Crear la base de datos**: Ejecuta el script SQL proporcionado en tu servidor MySQL
3. **Abrir el proyecto**: En tu IDE preferido (IntelliJ IDEA, Eclipse, etc.)
4. **Compilar el proyecto**:
   ```bash
   mvn clean compile
   ```
5. **Ejecutar la aplicación**:
   - Ejecuta la clase `Main.java` ubicada en `vallegrande.edu.pe`
   - O usa Maven:
     ```bash
     mvn exec:java -Dexec.mainClass="vallegrande.edu.pe.Main"
     ```

## Estructura del Proyecto
```
src/main/java/vallegrande/edu/pe/
├── Main.java                          # Punto de entrada de la aplicación
├── conexion/
│   └── ConexionMySQL.java            # Conexión a la base de datos
├── controller/
│   ├── LoginController.java           # Controlador para el login
│   ├── CursoController.java           # Controlador para cursos
│   └── EstudianteController.java      # Controlador para estudiantes
├── model/
│   ├── Usuario.java                   # Modelo de usuario
│   ├── UsuarioDAO.java                # DAO de usuario
│   ├── Curso.java                     # Modelo de curso
│   ├── CursoDAO.java                  # DAO de curso
│   ├── Estudiante.java                # Modelo de estudiante
│   └── EstudianteDAO.java             # DAO de estudiante
├── view/
│   ├── LoginView.java                 # Ventana de login
│   ├── VentanaBienvenida.java         # Ventana principal
│   ├── PanelCurso.java                # Panel de gestión de cursos
│   ├── DialogoCurso.java              # Diálogo para crear/editar cursos
│   ├── PanelEstudiante.java           # Panel de gestión de estudiantes
│   └── DialogoEstudiante.java         # Diálogo para crear/editar estudiantes
└── utils/
    └── Validador.java                 # Clase de validaciones
```

## Funcionalidades Principales
1. **Login**: Autenticación de usuarios
2. **Gestión de Cursos**: CRUD de cursos (crear, leer, actualizar, desactivar/activar)
3. **Gestión de Estudiantes**: CRUD de estudiantes (crear, leer, actualizar, desactivar/activar)
4. **Búsqueda**: Filtrado de cursos y estudiantes
5. **Soft Delete**: Desactivación en lugar de eliminación física

## Tecnologías Utilizadas
- **Java Swing**: Para la interfaz gráfica
- **MySQL Connector/J 8.0.33**: Para la conexión a la base de datos
- **Maven**: Para la gestión de dependencias
