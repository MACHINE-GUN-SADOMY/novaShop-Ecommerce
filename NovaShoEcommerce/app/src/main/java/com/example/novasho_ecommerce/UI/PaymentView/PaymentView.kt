package com.example.novasho_ecommerce.ui.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.novasho_ecommerce.viewmodel.CartViewModel

@Composable
fun PaymentView(
    cartVM: CartViewModel,
    onPaymentSuccess: () -> Unit,
    onPaymentCancel: () -> Unit
) {
    var address by remember { mutableStateOf("") }
    var commune by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    var addressErr by remember { mutableStateOf<String?>(null) }
    var communeErr by remember { mutableStateOf<String?>(null) }
    var cityErr by remember { mutableStateOf<String?>(null) }
    var postalCodeErr by remember { mutableStateOf<String?>(null) }

    fun validate() {
        addressErr = if (address.isBlank()) "Requerido" else null
        communeErr = if (commune.isBlank()) "Requerido" else null
        cityErr = if (city.isBlank()) "Requerido" else null
        postalCodeErr = if (postalCode.isBlank() || postalCode.length != 5) "Código Postal inválido" else null
    }

    val isValid by remember {
        derivedStateOf {
            addressErr == null && communeErr == null && cityErr == null && postalCodeErr == null
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Detalles de la Dirección de Envío", style = MaterialTheme.typography.headlineMedium)

        TextField(
            value = address,
            onValueChange = { address = it; validate() },
            label = { Text("Dirección") },
            isError = addressErr != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (addressErr != null) {
            Text(addressErr!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = commune,
            onValueChange = { commune = it; validate() },
            label = { Text("Comuna") },
            isError = communeErr != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (communeErr != null) {
            Text(communeErr!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = city,
            onValueChange = { city = it; validate() },
            label = { Text("Ciudad") },
            isError = cityErr != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (cityErr != null) {
            Text(cityErr!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = postalCode,
            onValueChange = { postalCode = it; validate() },
            label = { Text("Código Postal") },
            isError = postalCodeErr != null,
            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (postalCodeErr != null) {
            Text(postalCodeErr!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isValid) {
                    onPaymentSuccess()
                }
            },
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pagar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onPaymentCancel) {
            Text("Cancelar")
        }
    }
}
