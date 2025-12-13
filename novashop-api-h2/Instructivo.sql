-- 1. CREAR USUARIO (ADMIN o CUSTOMER)
--      ESTA DIFERENCIA SOLO APLICA AL MOMENTO DE USAR CIERTOS ENDPOINTS, EN LA APP FINAL NO TIENE DIFERENCIA
INSERT INTO usuarios (id, nombre, email, pasword, fecha_cre, rol) VALUES (
    1,
    'kristian',
    'nigger1@protonmail.com',
    '123456X',
    '2025-02-02 00:00:00',
    'ADMIN' -- AQUI SE PUEDE CAMBIAR A CUSTOMER
);


-- 2. CREAR PRODUCTO
INSERT INTO `productos`(`id`, `nombre`, `descripcion`, `precio`, `stock`, `categori_id`) VALUES 
	(1
	,'Pantalon NovaShop'
	,'Pantalon Novashop Talla 44'
	,12990
	,50
	,null
);

-- 3. Query
SELECT * FROM usuarios;
SELECT * FROM productos;

-- ! OJO SOLO PARA TRUNCAR TABLAS !
TRUNCATE TABLE CARRITOS;
TRUNCATE TABLE CARRITO_ITEMS;
TRUNCATE TABLE PEDIDOS;
TRUNCATE TABLE PEDIDO_ITEMS;
TRUNCATE TABLE PRODUCTOS;
TRUNCATE TABLE USUARIOS;