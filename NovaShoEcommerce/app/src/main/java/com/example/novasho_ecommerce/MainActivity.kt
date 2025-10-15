package com.example.novasho_ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier 
import com.example.novasho_ecommerce.UI.Order.OrderView
import com.example.novasho_ecommerce.UI.Order.SimpleOrder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OrderView(
                    orders = listOf(
                        SimpleOrder(1, "2025-09-25", 41650, "APROBADA"),
                        SimpleOrder(2, "2025-09-24", 53550, "RECHAZADA")
                    )
                )
            }
        }
    }
}
