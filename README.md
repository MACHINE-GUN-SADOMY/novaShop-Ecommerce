# 🛍️ NovaShop — API + Aplicación Móvil  
**Proyecto académico integrado — Backend en Spring Boot + MySQL & App Móvil Android con Kotlin/Compose**

---

# 📌 **Síntesis del Proyecto**

**NovaShop** es un sistema completo de comercio electrónico desarrollado como proyecto para la asignatura **Desarrollo de Aplicaciones Móviles**.  
Incluye:

### 🔹 **Una API REST en Spring Boot + MySQL**  
Encargada de manejar usuarios, productos, carrito, pedidos y la lógica de negocio.

### 🔹 **Una aplicación móvil Android creada con Kotlin + Jetpack Compose**  
Permite a los usuarios navegar por el catálogo, administrar su carrito, realizar compras y visualizar sus pedidos.

El objetivo del proyecto es demostrar una correcta integración entre las capas **frontend móvil**, **backend**, y **base de datos**, aplicando buenas prácticas de arquitectura como **MVVM**, **Repository Pattern**, y diseño limpio de API REST.

---

# 🏛️ Arquitectura del Sistema

El sistema está divido en 3 capas principales:

```
┌─────────────────────────────────────┐
│             APP MÓVIL               │
│ Kotlin + Compose (MVVM + Repo)      │
│ Pantallas: Login, Home, Productos,  │
│ Detalles, Carrito, Checkout, Pedidos│
└─────────────────────────────────────┘
                 │
                 ▼  (JSON / Retrofit)
┌─────────────────────────────────────┐
│             API REST                │
│ Java 21 – Spring Boot – JPA         │
│ Controladores, Servicios, Repos     │
│ Lógica de negocio y validaciones    │
└─────────────────────────────────────┘
                 │
                 ▼ (SQL)
┌─────────────────────────────────────┐
│          BASE DE DATOS              │
│ MySQL – Tablas normalizadas         │
│ Usuarios, Productos, Carritos,      │
│ Items, Pedidos, ItemsPedido         │
└─────────────────────────────────────┘
```

---

# 🚀 **Características de la API (Backend)**

### ✔ Autenticación básica
- Registro  
- Login  
- Retorno de datos del usuario

### ✔ CRUD de Productos  
- Crear  
- Actualizar  
- Listar  
- Eliminar  

### ✔ Carrito de Compras (FULL funcional)
Incluye:
- Crear carrito automáticamente si no existe
- Agregar productos
- Actualizar cantidad
- Eliminar item
- Vaciar carrito completo
- Obtener carrito con totales calculados

### Endpoints del carrito

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/carrito/{usuarioId}` | Obtiene o crea el carrito activo |
| POST | `/api/carrito/items` | Agrega un producto |
| PUT | `/api/carrito/items/{itemId}` | Modifica cantidad |
| DELETE | `/api/carrito/items/{itemId}` | Elimina item |
| DELETE | `/api/carrito/{carritoId}` | Vacía todo el carrito |

---

# 🛠️ **Tecnologías del Backend (API)**

- ☕ Java 21  
- 🚀 Spring Boot 3  
- 🗄️ MySQL  
- 🔗 JPA/Hibernate  
- 🧪 Postman para pruebas  
- 📁 Arquitectura por capas (Controller, Service, Repository, Model, DTO)

---

# 📱 **Características de la Aplicación Móvil**

### ✔ Catálogo dinámico  
- Lista de productos desde la API  
- Búsqueda y filtros  
- Imágenes y detalles

### ✔ Carrito de compras (totalmente integrado)
- Agregar productos  
- Modificar cantidad  
- Eliminar items  
- Vaciar carrito  
- Total calculado en tiempo real  

### ✔ Checkout  
- Formulario de datos de envío  
- Generación de orden  
- Estado inicial del pedido: *PENDIENTE*

### ✔ Órdenes  
- Listado de pedidos previos  
- Ver detalle  
- Incluye fecha, monto, dirección

---

# 🧩 **Tecnologías de la App Móvil**

- Kotlin  
- Jetpack Compose (Material 3)  
- MVVM + Repository Pattern  
- Retrofit  
- StateFlow / MutableStateFlow  
- Navigation Compose  
- Android Studio Giraffe+

---

# 🛠️ **Próximos pasos**

### Backend (API)
- Implementar **módulo de pedidos**
- Cambiar estados (PENDIENTE → PAGADO → ENVIADO)
- Agregar **JWT** para autenticación segura
- Stock dinámico y controlado

### App Móvil
- Interfaz de seguimiento de pedidos
- Validaciones avanzadas en checkout
- Guardar sesión con DataStore
- Mejor manejo de errores de red

---

# 👤 **Autor**
**Cristian Parra Hernández**  
📚 Asignatura: Desarrollo de Aplicaciones Móviles  
🏫 DuocUC – Sección 010V  
👨‍🏫 Docente: Luis David Becerra Uribe  

---

# 💼 **Propósito del Proyecto**
Este proyecto integra conocimientos de **bases de datos**, **backend**, **arquitectura MVVM**, **desarrollo móvil moderno**, y **consumo de APIs**.  
Representa una solución realista que demuestra habilidades técnicas completas y una arquitectura sólida para apps comerciales modernas.

---

