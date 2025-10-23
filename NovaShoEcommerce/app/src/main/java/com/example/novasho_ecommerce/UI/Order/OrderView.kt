package com.example.novasho_ecommerce.ui.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.novasho_ecommerce.Model.Order
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrdersView(
    orders: List<Order>,  // Lista de órdenes
    onBack: () -> Unit,   // Función para navegar hacia atrás
    dateFormat: SimpleDateFormat  // Formato de fecha para mostrar la fecha de creación
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,  // Usamos el icono estable de ArrowBack
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Órdenes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        if (orders.isEmpty()) {
            Text("No hay órdenes disponibles", Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(orders) { order ->
                    OrderItem(order = order, dateFormat = dateFormat)
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, dateFormat: SimpleDateFormat) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Orden #${order.id.toString().takeLast(6)}", style = MaterialTheme.typography.titleMedium)

        Text(text = "Fecha: ${dateFormat.format(Date(order.createdAt))}")

        Text(text = "Total: $${order.total}")

        Text(text = "Items: ${order.items.size}")

        Divider()
    }
}
