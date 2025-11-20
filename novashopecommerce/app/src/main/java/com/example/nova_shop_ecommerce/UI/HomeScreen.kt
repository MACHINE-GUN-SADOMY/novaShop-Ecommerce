package com.example.nova_shop_ecommerce.UI

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onGoCatalog: () -> Unit,
    onGoCart: () -> Unit,
    onGoCheckout: () -> Unit,
    onGoOrders: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("NovaShop", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Button(onClick = onGoCatalog, modifier = Modifier.fillMaxWidth()) { Text("Catálogo") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onGoCart, modifier = Modifier.fillMaxWidth()) { Text("Carrito") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onGoCheckout, modifier = Modifier.fillMaxWidth()) { Text("Checkout / Pago") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onGoOrders, modifier = Modifier.fillMaxWidth()) { Text("Órdenes") }
    }
}
