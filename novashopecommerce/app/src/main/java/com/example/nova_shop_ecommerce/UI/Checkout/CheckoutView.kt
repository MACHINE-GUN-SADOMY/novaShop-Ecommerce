package com.example.nova_shop_ecommerce.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.ViewModel.CarritoVM.CarritoViewModel

@Composable
fun CheckoutView(
    usuarioId: Long,
    cartVM: CarritoViewModel,
    onConfirm: (carritoId: Long, total: Int) -> Unit,
    onCancel: () -> Unit
) {
    val state by cartVM.carritoState.collectAsState()

    LaunchedEffect(usuarioId) {
        cartVM.cargarCarrito(usuarioId)
    }

    val items = state.carrito?.items.orEmpty()
    val total = state.carrito?.total ?: 0
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Checkout", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        if (state.loading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(12.dp))
        }

        if (!state.loading) {
            if (items.isEmpty()) {
                Text("No hay productos en el carrito")
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
            Text("CLP / ${"%.2f".format(total.toDouble())}", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.height(12.dp))

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        OutlinedButton(
            onClick = {
                println("Botón Volver presionado")
                onCancel()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Volver") }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (!cartVM.hasItems()) {
                    error = "No hay productos para pagar"
                    return@Button
                }
                error = null
                onConfirm(cartVM.getCarritoId(), cartVM.getTotal())
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.loading
        ) { Text("Ir a Pagar") }
    }
}