package com.example.nova_shop_ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nova_shop_ecommerce.ViewModel.CatalogVM.CatalogViewModel
import com.example.nova_shop_ecommerce.ViewModel.UsuarioVM.AuthViewModel
import com.example.nova_shop_ecommerce.ViewModel.PedidoVM.PedidoViewModel
import com.example.nova_shop_ecommerce.ViewModel.PaymentVM.PaymentViewModel
import com.example.nova_shop_ecommerce.UI.Auth.LoginScreen
import com.example.nova_shop_ecommerce.UI.Auth.RegisterScreen
import com.example.nova_shop_ecommerce.UI.Cart.CartView
import com.example.nova_shop_ecommerce.ViewModel.CarritoVM.CarritoViewModel
import com.example.nova_shop_ecommerce.UI.Catalog.CatalogView
import com.example.nova_shop_ecommerce.UI.Checkout.CheckoutView
import com.example.nova_shop_ecommerce.UI.Components.BottomNavigationBar
import com.example.nova_shop_ecommerce.UI.Order.OrdersView
import com.example.nova_shop_ecommerce.UI.PaymentView.PaymentScreen
import com.example.nova_shop_ecommerce.UI.MainPanelScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NovaShopApp() }
    }
}

@Composable
fun NovaShopApp() {
    val nav = rememberNavController()
    val authVM = androidx.lifecycle.viewmodel.compose.viewModel<AuthViewModel>()
    val catalogVM = androidx.lifecycle.viewmodel.compose.viewModel<CatalogViewModel>()
    val cartVM = androidx.lifecycle.viewmodel.compose.viewModel<CarritoViewModel>()
    val pedidoVM = androidx.lifecycle.viewmodel.compose.viewModel<PedidoViewModel>()
    val paymentVM = androidx.lifecycle.viewmodel.compose.viewModel<PaymentViewModel>()
    var usuarioId by rememberSaveable { mutableStateOf<Long?>(null) }

    NavHost(navController = nav, startDestination = "login") {

        composable("login") {
            LoginScreen(
                authVM = authVM,
                onLoginSuccess = { userId ->
                    usuarioId = userId
                    nav.navigate("main_panel") { popUpTo("login") { inclusive = true } }
                },
                onGoRegister = { nav.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                authVM = authVM,
                onRegisterSuccessSimple = { nav.navigate("login") },
                onGoLogin = { nav.popBackStack() }
            )
        }

        composable("main_panel") {
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }
            MainPanelScreen(
                navController = nav,
                usuarioId = uid
            )
        }

        // CATALOG
        composable("catalog") {
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }

            LaunchedEffect(uid) {
                cartVM.cargarCarrito(uid)
            }

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        navController = nav,
                        showBackOnly = false
                    )
                }
            ) { paddingValues ->
                CatalogView(
                    catalogVM = catalogVM,
                    cartVM = cartVM,
                    usuarioId = uid,
                    onGoToCart = {
                        cartVM.cargarCarrito(uid)
                        nav.navigate("cart")
                    },
                    paddingValues = paddingValues
                )
            }
        }

        /// CARRITO
        composable("cart") {
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }

            LaunchedEffect(uid) {
                cartVM.cargarCarrito(uid)
            }

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        navController = nav,
                        showBackOnly = false
                    )
                }
            ) { paddingValues ->
                CartView(
                    usuarioId = uid,
                    viewModel = cartVM,
                    onGoCheckout = { nav.navigate("checkout") },
                    paddingValues = paddingValues  // ¡Importante pasar paddingValues!
                )
            }
        }

        // CHECKOUT
        composable("checkout") {
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }
            LaunchedEffect(uid) {
                cartVM.cargarCarrito(uid)
            }

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        navController = nav,
                        showBackOnly = false
                    )
                }
            ) { paddingValues ->
                CheckoutView(
                    usuarioId = uid,
                    cartVM = cartVM,
                    onConfirm = { carritoId, total ->
                        nav.navigate("payment/$carritoId/$total")
                    },
                    onCancel = { nav.popBackStack("cart", inclusive = false) },
                    paddingValues = paddingValues  // ← Pasar paddingValues
                )
            }
        }

        // PAYMENT
        composable(
            route = "payment/{carritoId}/{total}",
            arguments = listOf(
                navArgument("carritoId") { type = NavType.LongType },
                navArgument("total") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }

            val carritoId = backStackEntry.arguments?.getLong("carritoId") ?: 0L
            val total = backStackEntry.arguments?.getInt("total") ?: 0

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        navController = nav,
                        showBackOnly = false
                    )
                }
            ) { paddingValues ->
                PaymentScreen(
                    usuarioId = uid,
                    carritoId = carritoId,
                    total = total,
                    paymentVM = paymentVM,
                    onBackToCheckout = { nav.popBackStack() },
                    onPaymentSuccess = {
                        cartVM.cargarCarrito(uid)
                        nav.navigate("orders") { popUpTo("home") { inclusive = false } }
                    },
                    paddingValues = paddingValues  // ← Pasar paddingValues
                )
            }
        }

        // ORDERS
        composable("orders") {
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        navController = nav,
                        showBackOnly = false
                    )
                }
            ) { paddingValues ->
                OrdersView(
                    pedidoVM = pedidoVM,
                    usuarioId = uid,
                    paddingValues = paddingValues
                )
            }
        }
    }
}