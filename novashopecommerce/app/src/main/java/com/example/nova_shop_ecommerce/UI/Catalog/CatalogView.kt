package com.example.nova_shop_ecommerce.UI

import com.example.nova_shop_ecommerce.Model.ProductoResponse
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.ViewModel.CatalogVM.CatalogViewModel
import com.example.nova_shop_ecommerce.ViewModel.CarritoVM.CarritoViewModel

@Composable
fun CatalogView(
    catalogVM: CatalogViewModel,
    cartVM: CarritoViewModel,
    usuarioId: Long,
    onGoToCart: () -> Unit,
    onGoHome: () -> Unit
) {
    val state by catalogVM.catalogState.collectAsState()

    LaunchedEffect(Unit) {
        catalogVM.loadProductos()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("Catálogo", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        when {
            state.loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.productos.isEmpty() -> {
                Text("No hay productos disponibles")
            }

            else -> {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onAddToCart = {
                                cartVM.agregarItem(
                                    usuarioId = usuarioId,
                                    productoId = producto.id,
                                    cantidad = 1
                                )
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = onGoHome,
                modifier = Modifier.weight(1f)
            ) {
                Text("Volver")
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = onGoToCart,
                modifier = Modifier.weight(1f)
            ) {
                Text("Ir al carrito")
            }
        }
    }
}

@Composable
private fun ProductoItem(
    producto: ProductoResponse,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text("Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            Button(onClick = onAddToCart) {
                Text("Agregar al carrito")
            }
        }
    }
}
