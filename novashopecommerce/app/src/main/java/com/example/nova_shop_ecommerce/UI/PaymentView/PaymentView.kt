package com.example.nova_shop_ecommerce.UI

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import com.example.nova_shop_ecommerce.ViewModel.PaymentVM.PaymentViewModel

@Composable
fun PaymentScreen(
    usuarioId: Long,
    carritoId: Long,
    total: Int,
    paymentVM: PaymentViewModel,
    onBackToCheckout: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val state by paymentVM.state.collectAsState()

    // Para navegar solo una vez cuando el pago sea exitoso
    var hasTriggeredSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(state.loading, state.error, state.pedido) {
        if (!state.loading && state.error == null && state.pedido != null && !hasTriggeredSuccess) {
            hasTriggeredSuccess = true
            onPaymentSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Pago", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.direccion,
            onValueChange = { paymentVM.actualizarDireccion(it) },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.comuna,
            onValueChange = { paymentVM.actualizarComuna(it) },
            label = { Text("Comuna") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.ciudad,
            onValueChange = { paymentVM.actualizarCiudad(it) },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Total: $total",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(24.dp))

        if (state.loading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
        }

        if (state.error != null) {
            Text(
                text = "Error: ${state.error}",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onBackToCheckout,
                modifier = Modifier.weight(1f)
            ) {
                Text("Volver")
            }

            Spacer(Modifier.width(8.dp))

            Button(
                enabled = !state.loading &&
                        state.direccion.isNotBlank() &&
                        state.comuna.isNotBlank() &&
                        state.ciudad.isNotBlank(),
                onClick = {
                    hasTriggeredSuccess = false
                    paymentVM.pagar(usuarioId, carritoId)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Pagar ahora")
            }
        }
    }
}
