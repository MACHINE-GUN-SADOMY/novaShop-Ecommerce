package com.example.novasho_ecommerce.UI.Checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.novasho_ecommerce.viewmodel.CartViewModel
import com.example.novasho_ecommerce.viewmodel.OrdersViewModel

@Composable
fun CheckoutView(
    cartVM: CartViewModel,
    ordersVM: OrdersViewModel,
    onSuccess: () -> Unit,
    onCancel: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }


    var nameErr by remember { mutableStateOf<String?>(null) }
    var emailErr by remember { mutableStateOf<String?>(null) }
    var addressErr by remember { mutableStateOf<String?>(null) }


    fun validate() {
        nameErr = if (name.isBlank()) "Requerido" else null
        emailErr = if (email.isBlank()) "Requerido" else if (!email.contains("@")) "Email inválido" else null
        addressErr = if (address.isBlank()) "Requerido" else null
    }


    val isValid = nameErr == null && emailErr == null && addressErr == null

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Total a pagar: $${cartVM.total { id -> 100 }}") // Ajusta este total según la lógica de tu carrito


        BasicTextField(value = name, onValueChange = { name = it; validate() })
        if (nameErr != null) Text(nameErr!!, color = MaterialTheme.colorScheme.error)


        BasicTextField(value = email, onValueChange = { email = it; validate() })
        if (emailErr != null) Text(emailErr!!, color = MaterialTheme.colorScheme.error)


        BasicTextField(value = address, onValueChange = { address = it; validate() })
        if (addressErr != null) Text(addressErr!!, color = MaterialTheme.colorScheme.error)


        Button(
            onClick = {
                if (isValid) {
                    ordersVM.addFromCart(cartVM.items) { id -> 100 } // Implementar el cálculo real del precio
                    onSuccess()
                }
            },
            enabled = isValid
        ) {
            Text("Confirmar Pedido")
        }


        Button(onClick = onCancel) {
            Text("Cancelar")
        }
    }
}
