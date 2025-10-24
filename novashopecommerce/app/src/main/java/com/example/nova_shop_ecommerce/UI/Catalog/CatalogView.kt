package com.example.nova_shop_ecommerce.UI.Catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.Model.Product
import com.example.nova_shop_ecommerce.viewmodel.CatalogViewModel
import com.example.nova_shop_ecommerce.viewmodel.CartViewModel

@Composable
fun CatalogView(
    catalogVM: CatalogViewModel,
    cartVM: CartViewModel,
    onGoToCart: () -> Unit,
    onGoHome: () -> Unit
) {
    val products by catalogVM.products.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Catálogo", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onGoHome) { Text("Menú") }
        }

        Spacer(Modifier.height(8.dp))

        if (products.isEmpty()) {
            Text("No hay productos disponibles")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(products) { product ->
                    ProductRow(product = product) { cartVM.add(product) }
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Button(onClick = onGoToCart, modifier = Modifier.fillMaxWidth()) {
            Text("Ir al Carrito")
        }
    }
}

@Composable
private fun ProductRow(product: Product, onAddToCart: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f).padding(end = 8.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = onAddToCart) { Text("Agregar") }
    }
}
