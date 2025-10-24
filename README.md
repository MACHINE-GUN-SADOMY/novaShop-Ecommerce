# 🛍️ NovaShop – E-Commerce App

**NovaShop** es una aplicación de comercio electrónico desarrollada en **Kotlin con Jetpack Compose**, siguiendo la arquitectura **MVVM** y el patrón **Repository**.  
El proyecto permite visualizar un catálogo de productos, agregar ítems al carrito, gestionar un flujo de checkout con validación de dirección y registrar órdenes con detalle de envío.

---

## 🚀 Características principales

- 📦 **Catálogo dinámico:** lista de productos con opción de búsqueda y filtrado.  
- 🛒 **Carrito de compras:** agrega, elimina y calcula totales automáticamente.  
- 💳 **Checkout con formulario:** solicita dirección, comuna, ciudad y región.  
- 📋 **Órdenes generadas:** lista de pedidos confirmados con fecha, monto y datos de envío.  
- 🧩 **Arquitectura MVVM + Repository:** separación clara entre capa UI, lógica y datos.  
- 🧱 **Compose Material 3:** interfaz moderna y adaptable.

---

## 🧠 Arquitectura del proyecto
com.example.nova_shop_ecommerce
├── Model/
│ ├── Product.kt
│ ├── CartItem.kt
│ ├── Order.kt
│ └── ShippingInfo.kt
├── Repository/
│ ├── CatalogRepository.kt
│ ├── CartRepository.kt
│ └── OrdersRepository.kt
├── ViewModel/
│ ├── CatalogViewModel.kt
│ ├── CartViewModel.kt
│ └── OrdersViewModel.kt
├── UI/
│ ├── HomeScreen.kt
│ ├── Catalog/
│ │ └── CatalogView.kt
│ ├── Cart/
│ │ └── CartView.kt
│ ├── Checkout/
│ │ └── CheckoutView.kt
│ └── Order/
│ └── 	OrdersView.kt
└── MainActivity.kt

yaml
Copiar código

---

## 🛠️ Tecnologías utilizadas

- **Lenguaje:** Kotlin  
- **Framework UI:** Jetpack Compose (Material 3)  
- **Arquitectura:** MVVM + Repository Pattern  
- **Gestión de estado:** StateFlow / MutableStateFlow  
- **Navegación:** Navigation Compose  
- **IDE:** Android Studio Giraffe+

---

## 🧾 Autor

**Cristian Parra Hernández**  
📚 Asignatura: *Desarrollo de Aplicaciones Móviles*  
🏫 DuocUC – Sección 010V  
👨‍🏫 Docente: *Luis David Becerra Uribe*  

---

## 📂 Repositorio

> 💡 [GitHub – NovaShop E-Commerce](https://github.com/usuario/NovaShop)  
> _(Reemplaza “usuario” por tu nombre de usuario real en GitHub)_