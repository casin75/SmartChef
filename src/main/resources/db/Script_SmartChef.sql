-- Creamos el esquema

CREATE SCHEMA SmartChef;

-- Indicamos que trabajaremos dentro de SmartChef

SET search_path TO SmartChef;


-- 1 Tabla receta
CREATE TABLE receta (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    dificultad INT,
    tiempo INT,
    raciones INT
);


-- 2 Tabla ingrediente
CREATE TABLE ingrediente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida INT NOT NULL,
    kcal NUMERIC(8, 2),
    precio NUMERIC(8, 2)
);


-- 3 Tabla usuario
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol INT NOT NULL
);


-- 4 Tabla preferencia
CREATE TABLE preferencia (
    id SERIAL PRIMARY KEY,
    tipo INT NOT NULL
);


-- 5 Tabla notificación
CREATE TABLE notificacion (
    id SERIAL PRIMARY KEY,
    tipo INT,
    texto TEXT NOT NULL
);


-- 6 Tabla pasos
CREATE TABLE pasos (
    id SERIAL PRIMARY KEY,
    numero INT NOT NULL,
    descripcion TEXT NOT NULL,
    id_receta INT NOT NULL,
    FOREIGN KEY (id_receta) REFERENCES receta(id) ON DELETE CASCADE
);


-- 7 Tabla fotos
CREATE TABLE fotos (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    id_receta INT NOT NULL,
    FOREIGN KEY (id_receta) REFERENCES receta(id) ON DELETE CASCADE
);


-- 8 Tabla coleccion
CREATE TABLE coleccion (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE
);


-- 9 Relación entre colecciones y recetas
CREATE TABLE coleccion_receta (
    id_coleccion INT NOT NULL,
    id_receta INT NOT NULL,
    PRIMARY KEY (id_coleccion, id_receta),
    FOREIGN KEY (id_coleccion) REFERENCES coleccion(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receta) REFERENCES receta(id) ON DELETE CASCADE
);


-- 10 Relación entre recetas e ingredientes
CREATE TABLE receta_ingrediente (
    id_receta INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad NUMERIC(8, 2) NOT NULL,
    PRIMARY KEY (id_receta, id_ingrediente),
    FOREIGN KEY (id_receta) REFERENCES receta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id) ON DELETE CASCADE
);


-- 11 Relación entre usuarios y recetas
CREATE TABLE usuario_receta (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_receta INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_receta) REFERENCES receta(id) ON DELETE CASCADE
);


-- 12 Despensa de ingredientes del usuario
CREATE TABLE despensa (
    id_usuario INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad NUMERIC(8, 2) NOT NULL,
    caducidad DATE,
    PRIMARY KEY (id_usuario, id_ingrediente),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id) ON DELETE CASCADE
);


-- 13 Lista de compra del usuario
CREATE TABLE usuario_ingrediente (
    id_usuario INT NOT NULL,
    id_ingrediente INT NOT NULL,
    PRIMARY KEY (id_usuario, id_ingrediente),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id) ON DELETE CASCADE
);


-- 14 Relación entre preferencias e ingredientes
CREATE TABLE preferencia_ingrediente (
    id_ingrediente INT NOT NULL,
    id_preferencia INT NOT NULL,
    PRIMARY KEY (id_ingrediente, id_preferencia),
    FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id) ON DELETE CASCADE,
    FOREIGN KEY (id_preferencia) REFERENCES preferencia(id) ON DELETE CASCADE
);


-- 15 Relación entre usuarios y preferencias
CREATE TABLE usuario_preferencia (
    id_usuario INT NOT NULL,
    id_preferencia INT NOT NULL,
    PRIMARY KEY (id_usuario, id_preferencia),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_preferencia) REFERENCES preferencia(id) ON DELETE CASCADE
);

/*
=========================================================
SECUENCIA DE BORRADO DE LAS TABLAS
=========================================================

DROP TABLE usuario_preferencia;
DROP TABLE preferencia_ingrediente;
DROP TABLE usuario_ingrediente;
DROP TABLE despensa;
DROP TABLE usuario_receta;
DROP TABLE receta_ingrediente;
DROP TABLE coleccion_receta;
DROP TABLE coleccion;
DROP TABLE fotos;
DROP TABLE pasos;
DROP TABLE notificacion;
DROP TABLE preferencia;
DROP TABLE usuario;
DROP TABLE ingrediente;
DROP TABLE receta;
DROP SCHEMA smartchef;
*/


/*
-- =========================================================
-- DATOS DE PRUEBA SMARTCHEF
-- =========================================================


-- =========================================================
-- 1. TABLA RECETA
-- =========================================================

INSERT INTO receta (nombre, descripcion, dificultad, tiempo, raciones) VALUES
('Paella de pollo', 'Arroz con pollo y verduras.', 3, 50, 4),
('Espaguetis boloñesa', 'Pasta con carne y salsa de tomate.', 2, 35, 4),
('Tortilla de patatas', 'Tortilla tradicional de patata y huevo.', 2, 30, 4),
('Ensalada César', 'Ensalada con pollo, lechuga y salsa César.', 1, 20, 2),
('Lasaña de carne', 'Lasaña con carne picada y bechamel.', 4, 70, 6),
('Gazpacho andaluz', 'Sopa fría de tomate y verduras.', 1, 15, 4),
('Pollo al curry', 'Pollo cocinado con curry y leche de coco.', 3, 40, 4),
('Salmón al horno', 'Salmón al horno con limón y patatas.', 2, 35, 2),
('Arroz con verduras', 'Arroz acompañado de verduras variadas.', 2, 30, 3),
('Hamburguesa casera', 'Hamburguesa de ternera con verduras.', 2, 25, 2);


-- =========================================================
-- 2. TABLA INGREDIENTE
-- =========================================================

INSERT INTO ingrediente (nombre, unidad_medida, kcal, precio) VALUES
('Arroz', 1, 360.00, 1.50),
('Pollo', 2, 165.00, 5.50),
('Patata', 1, 77.00, 1.20),
('Tomate', 1, 18.00, 1.80),
('Cebolla', 1, 40.00, 1.30),
('Pimiento rojo', 1, 31.00, 2.20),
('Ajo', 3, 149.00, 2.50),
('Aceite de oliva', 3, 884.00, 7.50),
('Huevo', 4, 155.00, 2.80),
('Carne picada', 2, 250.00, 6.50),
('Espaguetis', 1, 350.00, 1.70),
('Lechuga', 1, 15.00, 1.40);


-- =========================================================
-- 3. TABLA USUARIO
-- =========================================================

INSERT INTO usuario (nombre, contrasena, rol) VALUES
('Carlos García', 'Carlos123', 1),
('Lucía Fernández', 'Lucia123', 2),
('Miguel López', 'Miguel123', 2),
('Ana Martínez', 'Ana123', 1),
('David Sánchez', 'David123', 2),
('Laura Rodríguez', 'Laura123', 2),
('Pablo Gómez', 'Pablo123', 3),
('Marta Díaz', 'Marta123', 2),
('Javier Moreno', 'Javier123', 1),
('Sara Navarro', 'Sara123', 2);


-- =========================================================
-- 4. TABLA PREFERENCIA
-- =========================================================

INSERT INTO preferencia (tipo) VALUES
(1),
(2),
(3),
(4),
(5),
(1),
(2),
(3),
(4),
(5);


-- =========================================================
-- 5. TABLA NOTIFICACION
-- =========================================================

INSERT INTO notificacion (tipo, texto) VALUES
(1, 'Tienes una nueva receta recomendada.'),
(2, 'Uno de tus ingredientes está próximo a caducar.'),
(3, 'Tu lista de la compra ha sido actualizada.'),
(1, 'Hay nuevas recetas disponibles.'),
(4, 'Has añadido una receta a tu colección.'),
(2, 'Revisa los ingredientes de tu despensa.'),
(3, 'Tu receta favorita ha sido actualizada.'),
(1, 'Tenemos una receta que puede interesarte.'),
(4, 'Has guardado una nueva receta.'),
(2, 'Un ingrediente de tu despensa ha caducado.');


-- =========================================================
-- 6. TABLA PASOS
-- =========================================================

INSERT INTO pasos (numero, descripcion, id_receta) VALUES
(1, 'Preparar todos los ingredientes.', 1),
(2, 'Cortar el pollo y las verduras.', 1),
(3, 'Cocinar el arroz con los ingredientes.', 1),
(1, 'Cocer los espaguetis.', 2),
(2, 'Preparar la salsa de carne.', 2),
(3, 'Mezclar la pasta con la salsa.', 2),
(1, 'Pelar y cortar las patatas.', 3),
(2, 'Batir los huevos.', 3),
(3, 'Cocinar la tortilla en la sartén.', 3),
(1, 'Lavar y cortar la lechuga.', 4),
(2, 'Añadir el pollo y la salsa.', 4),
(3, 'Servir la ensalada.', 4);


-- =========================================================
-- 7. TABLA FOTOS
-- =========================================================

INSERT INTO fotos (url, id_receta) VALUES
('https://smartchef.com/fotos/paella.jpg', 1),
('https://smartchef.com/fotos/espaguetis.jpg', 2),
('https://smartchef.com/fotos/tortilla.jpg', 3),
('https://smartchef.com/fotos/ensalada_cesar.jpg', 4),
('https://smartchef.com/fotos/lasana.jpg', 5),
('https://smartchef.com/fotos/gazpacho.jpg', 6),
('https://smartchef.com/fotos/pollo_curry.jpg', 7),
('https://smartchef.com/fotos/salmon.jpg', 8),
('https://smartchef.com/fotos/arroz_verduras.jpg', 9),
('https://smartchef.com/fotos/hamburguesa.jpg', 10);


-- =========================================================
-- 8. TABLA COLECCION
-- =========================================================

INSERT INTO coleccion (nombre, id_usuario) VALUES
('Mis recetas favoritas', 1),
('Recetas rápidas', 2),
('Comida saludable', 3),
('Recetas familiares', 4),
('Cocina italiana', 5),
('Cenas fáciles', 6),
('Recetas para invitados', 7),
('Comida tradicional', 8),
('Mis recetas guardadas', 9),
('Recetas para el fin de semana', 10);


-- =========================================================
-- 9. TABLA COLECCION_RECETA
-- =========================================================

INSERT INTO coleccion_receta (id_coleccion, id_receta) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(1, 3),
(2, 5);


-- =========================================================
-- 10. TABLA RECETA_INGREDIENTE
-- =========================================================

INSERT INTO receta_ingrediente (id_receta, id_ingrediente, cantidad) VALUES
(1, 1, 300.00),
(1, 2, 400.00),
(1, 5, 100.00),
(2, 11, 300.00),
(2, 10, 250.00),
(2, 4, 200.00),
(3, 3, 500.00),
(3, 9, 5.00),
(3, 8, 50.00),
(4, 12, 200.00),
(4, 2, 250.00),
(4, 8, 20.00),
(5, 10, 400.00),
(5, 4, 300.00),
(5, 9, 3.00),
(6, 4, 500.00),
(6, 5, 100.00),
(6, 8, 30.00);


-- =========================================================
-- 11. TABLA USUARIO_RECETA
-- =========================================================

INSERT INTO usuario_receta (id_usuario, id_receta, fecha, tipo) VALUES
(1, 1, '2026-09-01 10:30:00', 1),
(2, 2, '2026-09-02 12:15:00', 2),
(3, 3, '2026-09-03 14:20:00', 1),
(4, 4, '2026-09-04 18:00:00', 3),
(5, 5, '2026-09-05 19:30:00', 1),
(6, 6, '2026-09-06 11:45:00', 2),
(7, 7, '2026-09-07 16:10:00', 1),
(8, 8, '2026-09-08 20:00:00', 3),
(9, 9, '2026-09-09 13:30:00', 2),
(10, 10, '2026-09-10 21:15:00', 1);


-- =========================================================
-- 12. TABLA DESPENSA
-- =========================================================

INSERT INTO despensa (id_usuario, id_ingrediente, cantidad, caducidad) VALUES
(1, 1, 2.00, '2026-10-15'),
(2, 2, 1.50, '2026-10-05'),
(3, 3, 3.00, '2026-10-10'),
(4, 4, 2.00, '2026-10-03'),
(5, 5, 1.00, '2026-10-08'),
(6, 6, 2.00, '2026-10-12'),
(7, 7, 0.50, '2026-10-02'),
(8, 8, 1.00, '2027-01-15'),
(9, 9, 12.00, '2026-10-20'),
(10, 10, 1.50, '2026-10-06');


-- =========================================================
-- 13. TABLA USUARIO_INGREDIENTE
-- =========================================================

INSERT INTO usuario_ingrediente (id_usuario, id_ingrediente) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(1, 4),
(2, 8);


-- =========================================================
-- 14. TABLA PREFERENCIA_INGREDIENTE
-- =========================================================

INSERT INTO preferencia_ingrediente (id_ingrediente, id_preferencia) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 1),
(7, 2),
(8, 3),
(9, 4),
(10, 5),
(11, 1),
(12, 2);


-- =========================================================
-- 15. TABLA USUARIO_PREFERENCIA
-- =========================================================

INSERT INTO usuario_preferencia (id_usuario, id_preferencia) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 1),
(7, 2),
(8, 3),
(9, 4),
(10, 5),
(1, 3),
(2, 4);
*/
