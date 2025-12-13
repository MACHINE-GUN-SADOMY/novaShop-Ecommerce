# 🛍️ NovaShop — API + Aplicación Móvil  
**Proyecto académico integrado — Backend en Spring Boot + MySQL & App Móvil Android con Kotlin/Compose**

---

# 📌 **Síntesis del Proyecto**

**NovaShop** es un sistema completo de comercio electrónico desarrollado como proyecto para la asignatura **Desarrollo de Aplicaciones Móviles**.  
Incluye:

### 🔹 **Una API REST en Spring Boot + MySQL**  
Encargada de manejar usuarios, productos, carrito, pedidos y la lógica de negocio. **La base de datos está lista para usarse** e incluye su propio instructivo `.sql` para insertar productos y usuarios.

### 🔹 **Una aplicación móvil Android creada con Kotlin + Jetpack Compose**  
Permite a los usuarios navegar por el catálogo, administrar su carrito, realizar compras y visualizar sus pedidos. **Implementa arquitectura simplificada con navegación unificada**.

El objetivo del proyecto es demostrar una correcta integración entre las capas **frontend móvil**, **backend**, y **base de datos**, aplicando buenas prácticas de arquitectura como **MVVM**, **Repository Pattern**, y diseño limpio de API REST.

---

# 🏛️ **Arquitectura del Sistema Actualizada**

El sistema está divido en 3 capas principales con una arquitectura simplificada:

```
┌─────────────────────────────────────┐
│          APP MÓVIL ANDROID          │
│ Kotlin + Compose (MVVM + Repo)      │
│                                      │
│ Pantallas Principales:               │
│ • AuthScreen (Login/Registro)        │
│ • MainPanelScreen (Pantalla Única)   │
│   - Catálogo                         │
│   - Carrito                          │
│   - Pedidos                          │
│   - Perfil                           │
│   - Gestión de Productos (Admin)     │
│                                      │
│ Navegación:                          │
│ • BottomNavigationBar unificada      │
│ • Sin lógica de roles en navegación  │
│ • Persistencia con DataStore         │
└─────────────────────────────────────┘
                 │
                 ▼  (JSON / Retrofit)
┌─────────────────────────────────────┐
│             API REST                 │
│ Java 21 – Spring Boot – JPA          │
│ Controladores, Servicios, Repos      │
│ Lógica de negocio y validaciones     │
│                                      │
│ Endpoints Implementados:              │
│ • /api/auth/* (Login/Registro)       │
│ • /api/productos/* (CRUD completo)   │
│ • /api/carrito/* (Gestión completa)  │
└─────────────────────────────────────┘
                 │
                 ▼ (SQL)
┌─────────────────────────────────────┐
│          BASE DE DATOS MySQL         │
│ Tablas normalizadas                  │
│ • Usuarios                           │
│ • Productos                          │
│ • Carritos                           │
│ • ItemsCarrito                       │
│ • Pedidos                            │
│ • ItemsPedido                        │
│                                      │
│ Script SQL completo incluido         │
│ con inserts iniciales                │
└─────────────────────────────────────┘
```

---

# 🚀 **Características de la API (Backend)**

### ✅ **Autenticación básica implementada**
- Registro de usuarios  
- Login con validación  
- Retorno de datos del usuario con rol (ADMIN/CUSTOMER)

### ✅ **CRUD de Productos completo**  
- Crear productos (solo ADMIN)  
- Actualizar productos (solo ADMIN)  
- Listar productos (todos los usuarios)  
- Eliminar productos (solo ADMIN)  
- Filtrado y búsqueda

### ✅ **Carrito de Compras (FULL funcional)**
- Crear carrito automáticamente si no existe
- Agregar productos al carrito
- Actualizar cantidad de items
- Eliminar items individuales
- Vaciar carrito completo
- Obtener carrito con totales calculados automáticamente

### ✅ **Base de Datos lista para producción**
- Script SQL completo para crear todas las tablas
- Relaciones definidas correctamente
- Inserts iniciales para usuarios y productos
- Estructura normalizada y optimizada

### **Endpoints principales implementados**

| Método | Ruta | Descripción | Acceso |
|--------|------|-------------|--------|
| POST | `/api/auth/register` | Registro de usuario | Público |
| POST | `/api/auth/login` | Login de usuario | Público |
| GET | `/api/productos` | Listar productos | Público |
| GET | `/api/productos/{id}` | Obtener producto | Público |
| POST | `/api/productos` | Crear producto | Solo ADMIN |
| PUT | `/api/productos/{id}` | Actualizar producto | Solo ADMIN |
| DELETE | `/api/productos/{id}` | Eliminar producto | Solo ADMIN |
| GET | `/api/carrito/{usuarioId}` | Obtener/carrito | Usuario autenticado |
| POST | `/api/carrito/items` | Agregar item | Usuario autenticado |
| PUT | `/api/carrito/items/{itemId}` | Modificar cantidad | Usuario autenticado |
| DELETE | `/api/carrito/items/{itemId}` | Eliminar item | Usuario autenticado |
| DELETE | `/api/carrito/{carritoId}` | Vaciar carrito | Usuario autenticado |

---

# 🛠️ **Tecnologías del Backend (API)**

- ☕ **Java 21**  
- 🚀 **Spring Boot 3**  
- 🗄️ **MySQL 8.0+**  
- 🔗 **JPA/Hibernate**  
- 🧪 **Postman** para pruebas de API  
- 📁 **Arquitectura por capas** (Controller, Service, Repository, Model, DTO)
- 🔒 **Validaciones** integradas
- 📊 **Base de datos** con script SQL completo incluido

---

# 📱 **Características de la Aplicación Móvil**

### ✅ **Arquitectura simplificada y optimizada**
- **Pantalla única MainPanelScreen** para todos los usuarios
- **Navegación unificada** sin lógica de roles
- **BottomNavigationBar** funcional en todas las vistas excepto PaymentView y CheckoutView
- **Persistencia de sesión** con DataStore implementada

### ✅ **Autenticación mejorada**
- Pantalla de Login/Registro (`AuthScreen`)
- ViewModel con DataStore (`AuthViewModel`)
- Persistencia automática de sesión
- Navegación directa a MainPanelScreen tras login

### ✅ **Gestión de Productos**
- Catálogo dinámico desde API
- CRUD completo para usuarios ADMIN
- Interfaz adaptativa según rol
- Estado reactivo con ViewModel

### ✅ **Carrito de compras (totalmente integrado)**
- Agregar productos desde catálogo  
- Modificar cantidad en tiempo real  
- Eliminar items individuales  
- Vaciar carrito completo  
- Total calculado automáticamente  

### ✅ **Navegación optimizada**
- `BottomNavigationBar` con 5 secciones:
  1. **Inicio** → MainPanelScreen
  2. **Catálogo** → CatalogView
  3. **Carrito** → CartView
  4. **Pedidos** → OrderView
  5. **Perfil** → SettingsScreen
- **Sección adicional para ADMIN**: Gestión de Productos
- Navegación consistente entre todas las vistas

### ✅ **Persistencia de datos**
- **DataStore** implementado para guardar sesión
- Información persistente: userId, email, role
- Carga automática de sesión al iniciar app
- Logout con limpieza de datos

---

# 🧩 **Tecnologías de la App Móvil**

- **Kotlin** como lenguaje principal
- **Jetpack Compose** con Material 3
- **Arquitectura MVVM** + Repository Pattern
- **Retrofit** para comunicación con API
- **StateFlow** / **MutableStateFlow** para estado reactivo
- **Navigation Compose** para navegación
- **DataStore** para persistencia local
- **Coroutines** para operaciones asíncronas
- **Android Studio Giraffe+** como IDE

---

# 🔧 **Configuración y Uso**

## **Backend (API Spring Boot)**

### Requisitos:
- Java 21
- MySQL 8.0+
- Maven

### Pasos de instalación:
1. Clonar repositorio `novashop-api`
2. Configurar `application.properties` con credenciales de MySQL
3. Ejecutar script SQL incluido para crear base de datos
4. Ejecutar `mvn spring-boot:run`
5. La API estará disponible en `http://localhost:8080`

### Script SQL incluido:
El proyecto incluye un archivo `database_setup.sql` con:
- Creación de todas las tablas
- Definición de relaciones y constraints
- Inserts iniciales para usuarios (admin y customer)
- Inserts iniciales de productos de ejemplo

## **Aplicación Móvil Android**

### Requisitos:
- Android Studio Giraffe+
- Android SDK 34
- minSdkVersion 25

### Configuración:
1. Abrir proyecto en Android Studio
2. Configurar URL de API en `ApiService.kt`
3. Sincronizar dependencias Gradle
4. Ejecutar en emulador o dispositivo físico

---

# 🎯 **Cambios Implementados Recientemente**

### **1. Arquitectura Simplificada**
- **Eliminada lógica de roles en navegación**: Ahora todos los usuarios ven la misma pantalla principal
- **MainPanelScreen única**: Reemplaza AdminPanelScreen y ClientePanelScreen
- **Navegación unificada**: Mismo flujo para todos los usuarios

### **2. Persistencia con DataStore**
- **AuthViewModel** actualizado con DataStore
- **Sesión persistente**: userId, email y role guardados localmente
- **Carga automática**: Sesión restaurada al abrir la aplicación
- **Logout completo**: Limpieza de datos locales

### **3. BottomNavigationBar Optimizada**
- **5 secciones principales** para todos los usuarios
- **Sección adicional** de gestión de productos solo visible para ADMIN
- **Navegación consistente** en todas las vistas
- **Ocultamiento automático** en PaymentView y CheckoutView

### **4. Corrección de Errores**
- **minSdkVersion** actualizado a 25
- **Rutas de navegación** corregidas en MainActivity
- **Importaciones** optimizadas y limpias
- **Lógica condicional** simplificada

### **5. Mejoras de UX**
- **Feedback visual** mejorado en operaciones de red
- **Estados de carga** implementados
- **Manejo de errores** más robusto
- **Navegación fluida** entre pantallas

---

# 📊 **Estado Actual del Proyecto**

### ✅ **Completado**
- [x] API REST funcional con Spring Boot
- [x] Base de datos MySQL con script completo
- [x] Autenticación básica (login/registro)
- [x] CRUD completo de productos
- [x] Gestión completa de carrito
- [x] Arquitectura MVVM en app móvil
- [x] Navegación con Compose
- [x] BottomNavigationBar funcional
- [x] Persistencia con DataStore
- [x] Pantalla única MainPanelScreen
- [x] Gestión de productos para ADMIN
- [x] El Proyecto se encuentra terminado.

# 👤 **Autor**
**Cristian Parra Hernández**  
📚 Asignatura: Desarrollo de Aplicaciones Móvil  
🏫 DuocUC – Sección 010V  
👨‍🏫 Docente: Luis David Becerra Uribe  

---

# 💼 **Propósito del Proyecto**
Este proyecto integra conocimientos de **bases de datos**, **backend**, **arquitectura MVVM**, **desarrollo móvil moderno**, y **consumo de APIs**.  
Representa una solución realista que demuestra habilidades técnicas completas y una arquitectura sólida para apps comerciales modernas.

**Características destacadas:**
- ✅ **Backend completo** con Spring Boot y MySQL
- ✅ **App móvil nativa** con Kotlin y Jetpack Compose
- ✅ **Arquitectura limpia** con separación de responsabilidades
- ✅ **Persistencia local** con DataStore
- ✅ **Navegación optimizada** y consistente
- ✅ **Documentación completa** y scripts SQL incluidos


---

# 🔗 **Recursos y Enlaces**

- **Repositorio Backend**: `https://github.com/tu-usuario/novashop-api`
- **Repositorio App Móvil**: `https://github.com/tu-usuario/novashop-android`
- **Documentación API**: Disponible en `/swagger-ui.html` al ejecutar el backend
- **Postman Collection**: Incluida en la carpeta `documentation/`

---

**📅 Última actualización:** Diciembre 2024  
**🚀 Estado del proyecto:** Funcional y listo para producción