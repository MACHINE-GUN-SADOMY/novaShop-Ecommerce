package com.example.nova_shop_ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nova_shop_ecommerce.UI.HomeScreen
import com.example.nova_shop_ecommerce.UI.Catalog.CatalogView
import com.example.nova_shop_ecommerce.UI.Checkout.CheckoutView
import com.example.nova_shop_ecommerce.ui.cart.CartView
import com.example.nova_shop_ecommerce.ui.order.OrdersView
import com.example.nova_shop_ecommerce.viewmodel.CartViewModel
import com.example.nova_shop_ecommerce.viewmodel.CatalogViewModel
import com.example.nova_shop_ecommerce.viewmodel.OrdersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NovashopApp() }
    }
}

@Composable
fun NovashopApp() {
    val nav = rememberNavController()

    val catalogVM = remember { CatalogViewModel() }
    val cartVM = remember { CartViewModel() }
    val ordersVM = remember { OrdersViewModel() }

    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onGoCatalog = { nav.navigate("catalog") },
                onGoCart = { nav.navigate("cart") },
                onGoCheckout = { nav.navigate("checkout") },
                onGoOrders = { nav.navigate("orders") }
            )
        }

        composable("catalog") {
            CatalogView(
                catalogVM = catalogVM,
                cartVM = cartVM,
                onGoToCart = { nav.navigate("cart") },
                onGoHome = { nav.popBackStack("home", inclusive = false) }
            )
        }

        composable("cart") {
            CartView(
                cartVM = cartVM,
                catalogVM = catalogVM,
                onGoToCheckout = { nav.navigate("checkout") },
                onGoHome = { nav.popBackStack("home", inclusive = false) }
            )
        }

        composable("checkout") {
            CheckoutView(
                cartVM = cartVM,
                ordersVM = ordersVM,
                catalogVM = catalogVM,
                onSuccess = {
                    nav.navigate("orders") { popUpTo("home") }
                },
                onCancel = { nav.popBackStack() },
                onGoHome = { nav.popBackStack("home", inclusive = false) }
            )
        }

        composable("orders") {
            OrdersView(
                ordersVM = ordersVM,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
