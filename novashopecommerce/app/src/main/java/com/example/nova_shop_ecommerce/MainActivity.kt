package com.example.nova_shop_ecommerce

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nova_shop_ecommerce.UI.CatalogView
import com.example.nova_shop_ecommerce.UI.CheckoutView
import com.example.nova_shop_ecommerce.UI.Order.OrdersView
import com.example.nova_shop_ecommerce.UI.PaymentScreen
import com.example.nova_shop_ecommerce.ViewModel.CatalogVM.CatalogViewModel
import com.example.nova_shop_ecommerce.ViewModel.UsuarioVM.AuthViewModel
import com.example.nova_shop_ecommerce.ViewModel.PedidoVM.PedidoViewModel
import com.example.nova_shop_ecommerce.ViewModel.PaymentVM.PaymentViewModel
import com.example.nova_shop_ecommerce.UI.HomeScreen
import com.example.nova_shop_ecommerce.UI.Auth.LoginScreen
import com.example.nova_shop_ecommerce.UI.Auth.RegisterScreen
import com.example.nova_shop_ecommerce.UI.Cart.CartView
import com.example.nova_shop_ecommerce.ViewModel.CarritoVM.CarritoViewModel

class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.values.all { it }
            if (allGranted) {
                setContent { NovaShopApp() }
            } else {
                setContent { NovaShopApp() }
            }
        }
        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.VIBRATE
        )
        val hasPermissions = permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }

        if (hasPermissions) {
            setContent { NovaShopApp() }
        } else {
            permissionLauncher.launch(permissions)
        }
    }
}

@Composable
fun NovaShopApp() {
    val nav = rememberNavController()
    val authVM = viewModel<AuthViewModel>()
    val catalogVM = viewModel<CatalogViewModel>()
    val cartVM = viewModel<CarritoViewModel>()
    val pedidoVM = viewModel<PedidoViewModel>()
    val paymentVM = viewModel<PaymentViewModel>()
    var usuarioId by rememberSaveable { mutableStateOf<Long?>(null) }

    @Composable
    fun hasPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.VIBRATE
        )
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                LocalContext.current,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    NavHost(navController = nav, startDestination = "login") {

        composable("login") {
            LoginScreen(
                authVM = authVM,
                onLoginSuccess = { userId ->
                    usuarioId = userId
                    nav.navigate("home") { popUpTo("login") { inclusive = true } }
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

        composable("home") {
            HomeScreen(
                onGoCatalog = { nav.navigate("catalog") },
                onGoCart = { nav.navigate("cart") },
                onGoCheckout = { nav.navigate("checkout") },
                onGoOrders = { nav.navigate("orders") }
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

            CatalogView(
                catalogVM = catalogVM,
                cartVM = cartVM,
                usuarioId = uid,
                onGoToCart = {
                    cartVM.cargarCarrito(uid)
                    nav.navigate("cart")
                },
                onGoHome = { nav.popBackStack("home", inclusive = false) }
            )
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

            CartView(
                usuarioId = uid,
                viewModel = cartVM,
                onGoCheckout = { nav.navigate("checkout") },
                onBack = { nav.popBackStack("home", inclusive = false) }
            )
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

            CheckoutView(
                usuarioId = uid,
                cartVM = cartVM,
                onConfirm = { carritoId, total ->
                    nav.navigate("payment/$carritoId/$total")
                },
                onCancel = { nav.popBackStack() }
            )
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

            PaymentScreen(
                usuarioId = uid,
                carritoId = carritoId,
                total = total,
                paymentVM = paymentVM,
                onBackToCheckout = { nav.popBackStack() },
                onPaymentSuccess = {
                    cartVM.cargarCarrito(uid)
                    nav.navigate("orders") { popUpTo("home") { inclusive = false } }
                }
            )
        }
        composable("orders") {
            val uid = usuarioId ?: run {
                nav.navigate("login") { popUpTo("login") { inclusive = true } }
                return@composable
            }
            OrdersView(
                pedidoVM = pedidoVM,
                usuarioId = uid,
                onBack = { nav.popBackStack("home", inclusive = false) }
            )
        }
    }
}