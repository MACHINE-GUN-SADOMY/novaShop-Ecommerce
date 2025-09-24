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
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {

                // Lista de Productos para probar
                val mockProducts = listOf(
                    Product(1, "Camiseta", 12000.0),
                    Product(2,"Pantalon", 6000.0),
                    Product(3,"Zapatillas", 15000.0))

                // Preview del Screen con sus parametros
                CatalogScreen(
                    products = mockProducts,
                    onAddToCart = {},
                    onGoToCart = {}
                )
            }
        }
    }
}
