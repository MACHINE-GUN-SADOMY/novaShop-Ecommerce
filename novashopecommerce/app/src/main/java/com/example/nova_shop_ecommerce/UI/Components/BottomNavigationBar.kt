package com.example.nova_shop_ecommerce.UI.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: @Composable () -> Unit
) {
    object Inicio : BottomNavItem(
        route = "main_panel",
        title = "Inicio",
        icon = { Icon(Icons.Filled.Start, contentDescription = "Inicio") }
    )

    object Catalog : BottomNavItem(
        route = "catalog",
        title = "Catálogo",
        icon = { Icon(Icons.Filled.Store, contentDescription = "Catálogo") }
    )

    object Cart : BottomNavItem(
        route = "cart",
        title = "Carrito",
        icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") }
    )

    object Checkout : BottomNavItem(
        route = "checkout",
        title = "Checkout",
        icon = { Icon(Icons.Filled.ShoppingBag, contentDescription = "Checkout") }
    )

    object Orders : BottomNavItem(
        route = "orders",
        title = "Órdenes",
        icon = { Icon(Icons.Filled.ListAlt, contentDescription = "Órdenes") }
    )

    object Back : BottomNavItem(
        route = "back",
        title = "Volver",
        icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") }
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    showBackOnly: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Inicio,
        BottomNavItem.Catalog,
        BottomNavItem.Cart,
        BottomNavItem.Checkout,
        BottomNavItem.Orders
    )
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (showBackOnly) {
            NavigationBarItem(
                selected = false,
                onClick = {
                    onBackClick?.invoke() ?: navController.popBackStack()
                },
                icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") },
                label = { Text("Volver") }
            )
        } else {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = item.icon,
                    label = { Text(item.title) }
                )
            }
        }
    }
}