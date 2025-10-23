package com.example.novasho_ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.novasho_ecommerce.Model.Order
import com.example.novasho_ecommerce.UI.Catalog.*
import com.example.novasho_ecommerce.UI.Cart.*
import com.example.novasho_ecommerce.UI.Checkout.*
import com.example.novasho_ecommerce.UI.HomeScreen
import com.example.novasho_ecommerce.UI.Order.*
import com.example.novasho_ecommerce.ViewModel.*
import com.example.novasho_ecommerce.ui.cart.CartView
import com.example.novasho_ecommerce.UI.Checkout.CheckoutView
import com.example.novasho_ecommerce.viewmodel.CartViewModel
import com.example.novasho_ecommerce.viewmodel.CatalogViewModel
import com.example.novasho_ecommerce.viewmodel.OrdersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovashoApp()
        }
    }
}

@Composable
fun NovashoApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("catalog") {
            CatalogView(
                catalogVM = CatalogViewModel(),
                cartVM = CartViewModel(),
                onAddToCart = { product -> CartViewModel().add(product.id) },  // Agregar al carrito
                onGoToCart = { navController.navigate("cart") }  // Navegar al carrito
            )
        }
        composable("cart") {
            CartView(
                cartVM = CartViewModel(),
                onGoToCheckout = { navController.navigate("checkout") } // Navegar a Checkout
            )
        }
        composable("checkout") {
            CheckoutView(
                cartVM = CartViewModel(),
                ordersVM = OrdersViewModel(),
                onSuccess = { navController.navigate("order") { popUpTo("home") } },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("order") {
            Order(ordersVM = OrdersViewModel(), onBack = { navController.popBackStack() })
        }
    }
}