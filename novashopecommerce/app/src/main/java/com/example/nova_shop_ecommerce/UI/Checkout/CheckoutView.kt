package com.example.nova_shop_ecommerce.UI.Checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nova_shop_ecommerce.Model.ShippingInfo
import com.example.nova_shop_ecommerce.viewmodel.CartViewModel
import com.example.nova_shop_ecommerce.viewmodel.CatalogViewModel
import com.example.nova_shop_ecommerce.viewmodel.OrdersViewModel

@Composable
fun CheckoutView(
    cartVM: CartViewModel,
    ordersVM: OrdersViewModel,
    catalogVM: CatalogViewModel,
    onSuccess: () -> Unit,
    onCancel: () -> Unit,
    onGoHome: () -> Unit
) {
    val items by cartVM.items.collectAsState()
    val total = cartVM.total { id -> catalogVM.getProductById(id)?.price ?: 0.0 }

    // Form fields
    var address by remember { mutableStateOf("") }
    var commune by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }

    val formValid = address.isNotBlank() && commune.isNotBlank() && city.isNotBlank() && region.isNotBlank()
    val cartEmpty = items.isEmpty()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Checkout", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onGoHome) { Text("Menú") }
        }

        Spacer(Modifier.height(8.dp))
        Text("Total a pagar: $${"%.2f".format(total)}", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))

        if (cartEmpty) {
            Text(
                "Tu carrito está vacío. Agrega productos antes de continuar.",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onGoHome) { Text("Ir al Menú") }
                TextButton(onClick = onCancel) { Text("Volver") }
            }
            return@Column
        }

        // Shipping form
        OutlinedTextField(
            value = address, onValueChange = { address = it },
            label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = commune, onValueChange = { commune = it },
            label = { Text("Comuna") }, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = city, onValueChange = { city = it },
            label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = region, onValueChange = { region = it },
            label = { Text("Región") }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                val shipping = ShippingInfo(address, commune, city, region)
                ordersVM.addFromCart(
                    cartItems = items,
                    resolveProduct = { id -> catalogVM.getProductById(id)!! },
                    shipping = shipping
                )
                cartVM.clear()
                onSuccess()
            },
            enabled = formValid && !cartEmpty,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar Pedido")
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onCancel, modifier = Modifier.align(Alignment.End)) {
            Text("Cancelar")
        }
    }
}
