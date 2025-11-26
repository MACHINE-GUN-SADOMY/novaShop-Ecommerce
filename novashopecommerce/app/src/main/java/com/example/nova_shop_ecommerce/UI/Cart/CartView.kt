package com.example.nova_shop_ecommerce.UI.Cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoItemResponse
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
                        CartItem(
                            item = item,
                            usuarioId = usuarioId,
                            viewModel = viewModel,
                            onError = { errorMsg -> error = errorMsg }
                        )
                        Divider()
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            Text("Total:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.weight(1f))
            Text("CLP ${"%.2f".format(total.toDouble())}", style = MaterialTheme.typography.titleMedium)
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

        if (items.isNotEmpty()) {
            Button(
                onClick = {
                    val carritoId = state.carrito?.carritoId ?: 0L
                    if (carritoId > 0) {
                        viewModel.vaciarCarrito(carritoId, usuarioId)
                    } else {
                        error = "No se puede vaciar el carrito"
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading
            ) { Text("Vaciar Carrito") }
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

@Composable
fun CartItem(
    item: CarritoItemResponse,
    usuarioId: Long,
    viewModel: CarritoViewModel,
    onError: (String) -> Unit
) {
    var cantidad by remember { mutableStateOf(item.cantidad) }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        // Información del producto
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(item.nombreProducto, style = MaterialTheme.typography.titleMedium)
            Text("Cantidad: ${item.cantidad}", style = MaterialTheme.typography.bodySmall)
            Text("Precio unit.: CLP ${"%.2f".format(item.precioUnitario.toDouble())}", style = MaterialTheme.typography.bodySmall)
            Text("Subtotal: CLP ${"%.2f".format(item.subtotal.toDouble())}", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(8.dp))

        // Botones de acción debajo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isEditing) {
                // Modo edición
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = cantidad.toString(),
                        onValueChange = { newValue ->
                            cantidad = newValue.toIntOrNull() ?: cantidad
                        },
                        label = { Text("Cantidad") },
                        modifier = Modifier.width(100.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (cantidad > 0) {
                                viewModel.modificarCantidad(item.itemId, cantidad, usuarioId)
                                isEditing = false
                            } else {
                                onError("La cantidad debe ser mayor a 0")
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            isEditing = false
                            cantidad = item.cantidad // Reset a valor original
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.outline
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            } else {
                // Modo normal
                Row {
                    Button(
                        onClick = { isEditing = true }
                    ) {
                        Text("Editar Cantidad")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.eliminarItem(item.itemId, usuarioId)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}