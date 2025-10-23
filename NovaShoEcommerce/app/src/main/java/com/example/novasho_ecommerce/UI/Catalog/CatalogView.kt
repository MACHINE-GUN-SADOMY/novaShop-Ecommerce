package com.example.novasho_ecommerce

import androidx.compose.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.novasho_ecommerce.Model.Product
import com.example.novasho_ecommerce.viewmodel.CartViewModel
import com.example.novasho_ecommerce.viewmodel.CatalogViewModel


@Composable
fun CatalogView(
    catalogVM: CatalogViewModel,
    cartVM: CartViewModel,
    onAddToCart: (Product) -> Unit,
    onGoToCart: () -> Unit
) {
    val products = catalogVM.products

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        if (products.isEmpty()) {
            Text("No hay productos disponibles")
        }

        LazyColumn {
            items(products) { product ->
                ProductRow(product = product, onAddToCart = { onAddToCart(product) })
            }
        }

        // Botón para ir al carrito
        Button(onClick = onGoToCart) {
            Text("Ir al Carrito")
        }
    }
}

@Composable
fun ProductRow(product: Product, onAddToCart: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = onAddToCart) {
            Text("Agregar")
        }
    }
}
