package com.example.nova_shop_ecommerce.UI.Cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.ViewModel.CarritoVM.CarritoViewModel

@Composable
fun CartView(
    usuarioId: Long,
    viewModel: CarritoViewModel,
    onGoCheckout: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    val state by viewModel.carritoState.collectAsState()

    LaunchedEffect(usuarioId) {
        viewModel.cargarCarrito(usuarioId)
    }

    val items = state.carrito?.items.orEmpty()
    val total = state.carrito?.total ?: 0
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Carrito", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        if (state.loading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(12.dp))
        }

        if (!state.loading) {
            if (items.isEmpty()) {
                Text("El carrito está vacío")
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f, fill = true),
                    contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                    items(items) { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Text(item.nombreProducto, style = MaterialTheme.typography.titleMedium)
                            Text("Cantidad: ${item.cantidad}", style = MaterialTheme.typography.bodySmall)
                            Text("Precio unit.: S/ ${"%.2f".format(item.precioUnitario.toDouble())}", style = MaterialTheme.typography.bodySmall)
                            Text("Subtotal: S/ ${"%.2f".format(item.subtotal.toDouble())}", style = MaterialTheme.typography.bodySmall)
                        }
                        Divider()
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            Text("Total:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.weight(1f))
            Text("S/ ${"%.2f".format(total.toDouble())}", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.height(8.dp))
        (state.error ?: error)?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        onBack?.let {
            Button(onClick = it, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (viewModel.hasItems()) {
                    error = null
                    onGoCheckout()
                } else {
                    error = "El carrito está vacío"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.loading
        ) { Text("Ir a Checkout") }
    }
}