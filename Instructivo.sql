-- 1. CREAR BASE DE DATOS NOVA SHOP
CREATE DATABASE `novaShop-ecommerce`;

-- 2. USAR BASE DE DATOS
USE `novaShop-ecommerce`;

-- 3. CREAR TABLAS
CREATE TABLE usuarios (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100)       NOT NULL,
    email       VARCHAR(100)       NOT NULL UNIQUE,
    pasword     VARCHAR(100)       NOT NULL,
    fecha_cre   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE categorias(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE productos(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio      DECIMAL(10,2) NOT NULL,
    stock       INT NOT NULL DEFAULT 0,
    categori_id BIGINT,
    FOREIGN KEY (categori_id) REFERENCES categorias(id)
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE carritos (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id    BIGINT NOT NULL,
    estado        ENUM('ACTIVO','CONVERTIDO','CANCELADO') DEFAULT 'ACTIVO',
    creado_en     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE carrito_items (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrito_id      BIGINT NOT NULL,
    producto_id     BIGINT NOT NULL,
    cantidad        INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (carrito_id) REFERENCES carritos(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE pedidos (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id    BIGINT NOT NULL,
    total         DECIMAL(10,2) NOT NULL,
    estado        ENUM('PENDIENTE','PAGADO','ENVIADO','CANCELADO') DEFAULT 'PENDIENTE',
    direccion_envio VARCHAR(255),
    creado_en     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    carrito_id    BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (carrito_id) REFERENCES carritos(id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE pedido_items (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id       BIGINT NOT NULL,
    producto_id     BIGINT NOT NULL,
    cantidad        INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- 4. Probar base de datos
INSERT INTO usuarios VALUES (
                             1,
                             'kristian',
                             'nigger1@protonmail.com',
                             '1234',
                             '02-02-02'
                             );

-- 5. Query
SELECT * FROM usuarios;