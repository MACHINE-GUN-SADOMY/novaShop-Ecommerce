package com.example.novasho_ecommerce

import androidx.compose.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


data class Product(val id: Int, val nombre: String, val precio: Double)

@Composable
fun CatalogScreen(products: List<Product>, onAddToCart: (Product) -> Unit, onGoToCart: () -> Unit) {
    Column {
        Text("Catalogo de Productos")

        products.forEach { product ->

            Row {
                Text("${product.nombre} - $${product.precio}", modifier = Modifier.weight(1f))
                Button(onClick = { onAddToCart(product) }) {
                    Text("Agregar")
                }
            }
        }
        Button(onClick = onGoToCart) {
            Text("Ver Carrito")
        }
    }
}
