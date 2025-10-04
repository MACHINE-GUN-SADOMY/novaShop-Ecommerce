package com.example.novasho_ecommerce.UI.Order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.novasho_ecommerce.Model.Cart
import com.example.novasho_ecommerce.Model.CartItem
import com.example.novasho_ecommerce.Model.Order
import com.example.novasho_ecommerce.Model.Product

@Composable
    fun OrderView(
    orders: List<SimpleOrder>,
    modifier: Modifier = Modifier
) {
    Column (modifier.padding(16.dp)){
        Text("Ordenes", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        if (orders.isEmpty()){
            Text("Aun no has realizado compras")
        }else{
            orders.forEach { o ->
                Card(Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                    Column(Modifier.padding(12.dp)) {

                        Text("Folio #${o.id}")
                        Spacer(Modifier.height(4.dp))

                        Text("Fecha #${o.date}")
                        Spacer(Modifier.height(4.dp))

                        Text("Total: $${o.total}")
                    }
                }
            }
        }
    }
}

data class SimpleOrder(
    val id: Int,
    val date: String,
    val total: Int,
    val status: String)

@Preview(showBackground = true)
@Composable
private fun OrdersScreenPreview() {
    val mock = listOf(
        SimpleOrder(1, "2025-09-25", 41650, "APROBADA"),
        SimpleOrder(2, "2025-09-24", 53550, "RECHAZADA")
    )
    MaterialTheme { OrderView(orders = mock) }
}