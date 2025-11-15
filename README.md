# 🛍️ NovaShop — API + Aplicación Móvil  
**Proyecto académico integrado — Backend en Spring Boot + MySQL & App Móvil Android con Kotlin/Compose**

---

# 📌 **Síntesis del Proyecto**

**NovaShop** es un sistema completo de comercio electrónico creado para la asignatura de **Desarrollo de Aplicaciones Móviles**.  
El sistema está compuesto por:

### 🔹 **API REST en Spring Boot + MySQL**
Encargada de manejar usuarios, productos, carritos, pedidos y la lógica de negocio.

### 🔹 **Aplicación móvil Android en Kotlin + Jetpack Compose**
Permite a los usuarios navegar por productos, administrar su carrito y realizar compras.

---

# 🏛️ Arquitectura del Sistema

```
┌─────────────────────────────────────┐
│             APP MÓVIL               │
│ Kotlin + Compose (MVVM + Repo)      │
│ Pantallas: Login, Home, Productos,  │
│ Detalle, Carrito, Checkout, Pedidos │
└─────────────────────────────────────┘
                 │
                 ▼ (JSON via Retrofit)
┌─────────────────────────────────────┐
│               API REST              │
│ Spring Boot – JPA – MySQL           │
│ Controllers / Services / Repos      │
│ Lógica de negocio y validaciones    │
└─────────────────────────────────────┘
                 │
                 ▼ (SQL)
┌─────────────────────────────────────┐
│            BASE DE DATOS            │
│ MySQL – Tablas normalizadas         │
│ Usuarios, Productos, Carritos,      │
│ Items, Pedidos, PedidoItems         │
└─────────────────────────────────────┘
```

---

# 🚀 **Características del Backend (API)**

---

# 🔐 Endpoints del Módulo de Autenticación (Auth)

| Método | Ruta | Descripción |
|--------|------|-------------|
| **POST** | `/api/auth/register` | Registrar un nuevo usuario |
| **POST** | `/api/auth/login` | Iniciar sesión |
| **GET**  | `/api/auth/{id}` | Obtener datos del usuario por ID |

---

# 🛒 Endpoints del Módulo de Productos

| Método | Ruta | Descripción |
|--------|------|-------------|
| **POST** | `/api/productos/crear?userId=XX` | Crear producto (solo Admin) |
| **GET**  | `/api/productos` | Listar todos los productos |
| **GET**  | `/api/productos/{id}` | Obtener producto por ID |
| **PUT**  | `/api/productos/actualizar/{id}?userId=XX` | Actualizar producto (solo Admin) |
| **DELETE** | `/api/productos/delete/{id}?userId=XX` | Eliminar producto (solo Admin) |

---

# 🛍️ Endpoints del Módulo de Carrito

| Método | Ruta | Descripción |
|--------|------|-------------|
| **GET** | `/api/carrito/{usuarioId}` | Obtener o crear carrito del usuario |
| **POST** | `/api/carrito/items` | Agregar item al carrito |
| **PUT** | `/api/carrito/items/{itemId}` | Modificar cantidad |
| **DELETE** | `/api/carrito/items/{itemId}` | Eliminar un item del carrito |
| **DELETE** | `/api/carrito/{carritoId}` | Vaciar completamente el carrito |

---

# 📦 Endpoints del Módulo de Pedidos

| Método | Ruta | Descripción |
|--------|------|-------------|
| **POST** | `/api/pedidos` | Crear pedido desde el carrito (checkout real) |
| **POST** | `/api/pedidos/{id}/pagar` | Pagar pedido (simulado) |
| **POST** | `/api/pedidos/{id}/cancelar?usuarioId=XX` | Cancelar pedido (solo PENDIENTE) |
| **GET**  | `/api/pedidos/{id}` | Obtener pedido por ID |
| **GET**  | `/api/pedidos/usuario/{usuarioId}` | Historial de pedidos del usuario |

---

# 🧩 **Tecnologías Backend**

- Java 21  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- MySQL  
- Postman para pruebas  
- Arquitectura por capas  

---

# 📱 **Características de la App Móvil**

- Kotlin  
- Jetpack Compose  
- StateFlow / MutableStateFlow  
- Navigation Compose  
- MVVM completo  
- Retrofit  
- Pantallas: Login, Registro, Home, Detalle, Carrito, Checkout, Pedidos  

---

# 👤 **Autor**
**Cristian Parra Hernández**  
DuocUC – Desarrollo de Aplicaciones Móviles – Sección 010V  
Docente: Luis David Becerra Uribe

---

# 🎯 **Resumen Final**
NovaShop es un proyecto completo que integra API REST profesional, arquitectura móvil moderna, JPA, SQL y patrones reales de backend aplicados a una app comercial moderna.
