package com.example.nova_shop_ecommerce.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.Model.PedidoResponse
import com.example.nova_shop_ecommerce.ViewModel.PedidoVM.PedidoViewModel

@Composable
fun OrdersView(
    pedidoVM: PedidoViewModel,
    usuarioId: Long,
    onBack: () -> Unit
) {
    val state by pedidoVM.pedidoState.collectAsState()

    LaunchedEffect(usuarioId) {
        pedidoVM.obtenerPedidosUsuario(usuarioId)
    }

    Column(Modifier.fillMaxSize()) {

        Surface(
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
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

        when {
            state.loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            state.listaPedidos.isEmpty() -> {
                Text(
                    "No hay órdenes disponibles",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(state.listaPedidos) { pedido ->
                        PedidoItem(pedido)
                    }
                }
            }
        }
    }
}

@Composable
private fun PedidoItem(pedido: PedidoResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text("Pedido #${pedido.id}", style = MaterialTheme.typography.titleMedium)

            val estadoColor = when (pedido.estado.uppercase()) {
                "PAGADO"     -> MaterialTheme.colorScheme.primary
                "PENDIENTE"  -> MaterialTheme.colorScheme.tertiary
                "CANCELADO"  -> MaterialTheme.colorScheme.error
                else         -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            Text(
                text = "Estado: ${pedido.estado}",
                color = estadoColor
            )

            Text("Total: $${pedido.total}")
            Text("Items: ${pedido.items.size}")
            Text("Fecha: ${pedido.fechaCreacion}")
        }
    }
}
