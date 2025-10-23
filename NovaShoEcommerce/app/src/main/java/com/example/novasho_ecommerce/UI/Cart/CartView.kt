package com.example.novasho_ecommerce.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.novasho_ecommerce.viewmodel.CartViewModel

@Composable
fun CartView(cartVM: CartViewModel, onGoToCheckout: () -> Unit) {
    val items = cartVM.items
    val total = cartVM.total { id -> 100 } // Ajusta según el total real

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        if (items.isEmpty()) {
            Text("Tu carrito está vacío")
        } else {
            items.forEach { (id, qty) ->
                Text("Producto $id - Cantidad: $qty")
            }
            Spacer(Modifier.height(16.dp))
            Text("Total: $$total")
        }
        Button(onClick = onGoToCheckout) {
            Text("Ir a Checkout")
        }
    }
}
