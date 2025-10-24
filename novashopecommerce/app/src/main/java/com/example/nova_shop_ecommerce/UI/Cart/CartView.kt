package com.example.nova_shop_ecommerce.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.viewmodel.CartViewModel
import com.example.nova_shop_ecommerce.viewmodel.CatalogViewModel

@Composable
fun CartView(
    cartVM: CartViewModel,
    catalogVM: CatalogViewModel,
    onGoToCheckout: () -> Unit,
    onGoHome: () -> Unit
) {
    val items by cartVM.items.collectAsState()

    val total = cartVM.total { id ->
        catalogVM.getProductById(id)?.price ?: 0.0
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Carrito", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onGoHome) { Text("Menú") }
        }

        Spacer(Modifier.height(8.dp))

        if (items.isEmpty()) {
            Text("Tu carrito está vacío")
        } else {
            items.forEach { (id, qty) ->
                val p = catalogVM.getProductById(id)
                if (p != null) {
                    Text("${p.name} x$qty = $${"%.2f".format(p.price * qty)}")
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Total: $${"%.2f".format(total)}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onGoToCheckout,
            enabled = items.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Checkout")
        }
    }
}
