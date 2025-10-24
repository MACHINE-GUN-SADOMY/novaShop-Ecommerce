package com.example.nova_shop_ecommerce.ui.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.viewmodel.OrdersViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersView(
    ordersVM: OrdersViewModel,
    onBack: () -> Unit
) {
    val orders by ordersVM.orders.collectAsState()
    val dateFormat = rememberDateFormat()

    Column(Modifier.fillMaxSize()) {
        // Header personalizado (sin TopAppBar)
        Surface(
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars) // respeta notch/status bar
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
                Spacer(Modifier.width(4.dp))
                Text("Órdenes", style = MaterialTheme.typography.titleLarge)
            }
        }

        if (orders.isEmpty()) {
            Text(
                "No hay órdenes disponibles",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(orders) { order ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Orden #${order.id.toString().takeLast(6)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Fecha: ${dateFormat.format(Date(order.createdAt))}")
                        Text("Total: $${"%.2f".format(order.total)}")
                        Text("Items: ${order.items.size}")
                        Spacer(Modifier.height(6.dp))
                        // Envío
                        Text("Envío:", style = MaterialTheme.typography.labelLarge)
                        Text("• Dirección: ${order.shipping.address}")
                        Text("• Comuna: ${order.shipping.commune}")
                        Text("• Ciudad: ${order.shipping.city}")
                        Text("• Región: ${order.shipping.region}")
                        Divider(Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberDateFormat(): SimpleDateFormat {
    return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
}
