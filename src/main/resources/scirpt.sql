
CREATE DATABASE IF NOT EXISTS vallegrande_db_POO;
USE vallegrande_db_POO;

-- ============================================
-- TABLA: usuario
-- ============================================
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    rol ENUM('admin', 'consultor') DEFAULT 'admin',
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME NULL,
    CONSTRAINT chk_nombre_usuario_long CHECK (LENGTH(nombre_usuario) >= 3)
);

-- Insertar usuario administrador por defecto
INSERT INTO usuario (nombre_usuario, contrasena, email, rol, activo)
VALUES ('admin', 'admin123', 'admin@vallegrande.edu.pe', 'admin', TRUE)
ON DUPLICATE KEY UPDATE id_usuario = id_usuario;

-- ============================================
-- TABLA: curso
-- ============================================
CREATE TABLE IF NOT EXISTS curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    creditos TINYINT NOT NULL CHECK (creditos BETWEEN 1 AND 10),
    horas_semana TINYINT NOT NULL CHECK (horas_semana BETWEEN 1 AND 40),
    descripcion TEXT,
    fecha_registro DATE NOT NULL DEFAULT (CURDATE()),
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_codigo_formato CHECK (codigo REGEXP '^[A-Z0-9]{3,10}$'),  
    CONSTRAINT chk_nombre_no_vacio CHECK (nombre <> '')
);
-- opcional: solo letras mayúsculas y números
-- ============================================
-- TABLA: estudiante (con tipo_documento y numero_documento)
-- ============================================
CREATE TABLE IF NOT EXISTS estudiante (
    id_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento ENUM('DNI', 'CE', 'Pasaporte') NOT NULL DEFAULT 'DNI',
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    fecha_nacimiento DATE,
    genero ENUM('M', 'F') NOT NULL,   -- Solo M o F
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_numero_no_vacio CHECK (numero_documento <> ''),
    CONSTRAINT chk_email_valido CHECK (email LIKE '%@%.%'),
    CONSTRAINT chk_telefono_digitos CHECK (telefono IS NULL OR telefono REGEXP '^[0-9]{7,15}$')
);

-- ============================================
-- TABLA: matricula
-- ============================================
CREATE TABLE IF NOT EXISTS matricula (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    id_curso INT NOT NULL,
    fecha_matricula DATE NOT NULL DEFAULT (CURDATE()),
    ciclo VARCHAR(10) NOT NULL COMMENT 'Ej: 2025-I, 2025-II',
    nota_final DECIMAL(4,2) NULL CHECK (nota_final BETWEEN 0 AND 20),
    estado_matricula ENUM('activa', 'completada', 'anulada') DEFAULT 'activa',
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT unique_matricula_estudiante_curso UNIQUE (id_estudiante, id_curso, ciclo)
);

-- Índices
CREATE INDEX idx_curso_activo ON curso(activo);
CREATE INDEX idx_estudiante_activo ON estudiante(activo);
CREATE INDEX idx_matricula_estudiante ON matricula(id_estudiante);
CREATE INDEX idx_matricula_curso ON matricula(id_curso);
CREATE INDEX idx_usuario_activo ON usuario(activo);

-- ============================================
-- Datos de ejemplo (cursos y estudiantes)
-- ============================================
INSERT INTO curso (codigo, nombre, creditos, horas_semana, descripcion) VALUES
('INF101', 'Programación I', 4, 5, 'Fundamentos de programación con Java'),
('INF102', 'Base de Datos', 4, 5, 'Diseño y administración de bases de datos relacionales'),
('MAT201', 'Matemática Aplicada', 3, 4, 'Álgebra y cálculo para informática');

-- Nota: ahora usamos tipo_documento y numero_documento, y género M/F
INSERT INTO estudiante (tipo_documento, numero_documento, nombres, apellidos, email, telefono, fecha_nacimiento, genero) VALUES
('DNI', '12345678', 'Juan Carlos', 'Pérez Gómez', 'juan.perez@example.com', '987654321', '2000-05-15', 'M'),
('CE', 'E87654321', 'María José', 'López Torres', 'maria.lopez@example.com', '912345678', '2001-08-22', 'F');

-- Matrículas de ejemplo (asegurar que los id_estudiante e id_curso existan)
INSERT INTO matricula (id_estudiante, id_curso, ciclo, fecha_matricula) VALUES
(1, 1, '2025-I', '2025-03-01'),
(1, 2, '2025-I', '2025-03-01'),
(2, 1, '2025-I', '2025-03-02')
ON DUPLICATE KEY UPDATE id_matricula = id_matricula;

-- Verificaciones
SELECT * FROM usuario;
SELECT * FROM curso;
SELECT * FROM estudiante;
SELECT * FROM matricula;