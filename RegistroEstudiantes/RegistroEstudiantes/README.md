# 📚 Sistema de Registro de Estudiantes — Java Swing

Formulario funcional desarrollado en Java Swing que integra los componentes principales de la biblioteca gráfica y aplica los cuatro pilares de la Programación Orientada a Objetos.

---

## 🧩 Componentes Swing utilizados

| Componente      | Uso en el proyecto |
|----------------|--------------------|
| `JFrame`        | Ventana principal (herencia directa) |
| `JPanel`        | Secciones del formulario (header, datos, intereses, footer) |
| `JLabel`        | Etiquetas de cada campo y contador de registros |
| `JTextField`    | Campos de nombre, apellido, correo y carrera |
| `JOptionPane`   | Diálogos de éxito, error, advertencia y confirmación de salida |
| `JCheckBox`     | Selección de áreas de interés y aceptación de términos |

---

## 🏗️ Pilares de la POO aplicados

| Pilar             | Implementación |
|-------------------|----------------|
| **Encapsulamiento** | Todos los campos de la UI y la lista de estudiantes son `private`. Acceso únicamente mediante métodos públicos. |
| **Abstracción**     | La UI está construida por métodos con nombre descriptivo (`crearPanelHeader`, `crearSeccionIntereses`, etc.). La lógica de negocio vive en `EstudianteServicio` separado de la vista. |
| **Herencia**        | `FormularioEstudiante extends JFrame` hereda el comportamiento completo de ventana. |
| **Polimorfismo**    | Los tres botones implementan `ActionListener` mediante lambdas. `toString()` está sobrescrito en `Estudiante`. |

---

## 📁 Estructura del proyecto

```
RegistroEstudiantes/
│
├── src/
│   ├── formulario/
│   │   ├── Main.java                    ← Punto de entrada (main)
│   │   └── FormularioEstudiante.java    ← Vista principal (JFrame + todos los componentes)
│   │
│   ├── modelo/
│   │   └── Estudiante.java              ← Clase entidad con getters/setters
│   │
│   ├── servicio/
│   │   └── EstudianteServicio.java      ← Lógica de negocio y validaciones
│   │
│   └── util/
│       └── Validador.java               ← Métodos estáticos de validación
│
├── .gitignore
└── README.md
```

---

## ▶️ Cómo compilar y ejecutar

### Desde la línea de comandos (sin IDE)

```bash
# 1. Entrar a la carpeta del proyecto
cd RegistroEstudiantes

# 2. Compilar todos los archivos fuente
javac -d out -sourcepath src src/formulario/Main.java

# 3. Ejecutar la aplicación
java -cp out formulario.Main
```

### Desde un IDE (IntelliJ IDEA / Eclipse / NetBeans)

1. Abrir el IDE y seleccionar **"Abrir proyecto"** o **"Import project"**.
2. Navegar hasta la carpeta `RegistroEstudiantes/src`.
3. Marcar `src` como **Source Root** (carpeta raíz de fuentes).
4. Ejecutar la clase `formulario.Main`.

---

## ✅ Funcionalidades del formulario

- **Registro de estudiantes** con nombre, apellido, correo y carrera.
- **Código automático** generado por el sistema (formato `EST-YYYY-NNN`).
- **Áreas de interés** seleccionables mediante `JCheckBox` (6 opciones).
- **Validaciones activas**:
  - Nombre y apellido: solo letras.
  - Correo: formato válido con `@` y dominio.
  - Carrera: mínimo 3 caracteres.
  - Al menos un interés seleccionado.
  - Aceptación de términos obligatoria.
- **Confirmación de salida** con conteo de registros de la sesión.
- **Limpiar formulario** con un solo botón.

---

## 👥 Requisitos

- Java 8 o superior
- No requiere librerías externas

---

## 📝 Notas académicas

Este proyecto fue desarrollado como actividad grupal para la asignatura de **Programación Orientada a Objetos** con Java.

- **Tecnología**: Java SE + Java Swing
- **Patrón de arquitectura**: Separación por capas (Vista / Servicio / Modelo / Utilidades)
