package com.example.nova_shop_ecommerce.UI

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nova_shop_ecommerce.UI.Components.BottomNavigationBar
import com.example.nova_shop_ecommerce.UI.Components.BottomNavItem

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val description: String
)

@Composable
fun MainPanelScreen(
    navController: NavController,
    usuarioId: Long,
    modifier: Modifier = Modifier.fillMaxSize(),
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val quickActions = listOf(
        QuickAction(
            title = "Catálogo",
            icon = Icons.Filled.Store,
            route = "catalog",
            description = "Explora nuestros productos"
        ),
        QuickAction(
            title = "Carrito",
            icon = Icons.Filled.ShoppingCart,
            route = "cart",
            description = "Revisa tus productos"
        ),
        QuickAction(
            title = "Órdenes",
            icon = Icons.Filled.ListAlt,
            route = "orders",
            description = "Historial de compras"
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = listOf(
                    BottomNavItem.Inicio,
                    BottomNavItem.Catalog,
                    BottomNavItem.Cart,
                    BottomNavItem.Checkout,
                    BottomNavItem.Orders
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header con bienvenida
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center   // ¡Esto centra verticalmente!
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Usuario",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Bienvenido",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "Panel Principal",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Sección de acciones rápidas
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Acciones Rápidas",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    quickActions.forEach { action ->
                        QuickActionCard(
                            action = action,
                            navController = navController,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Información útil
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Información Importante",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoItem(
                            icon = Icons.Filled.Info,
                            text = "Estados de pedido: PAGADO, PENDIENTE o CANCELADO"
                        )
                        InfoItem(
                            icon = Icons.Filled.History,
                            text = "Revisa el estado de tus pedidos en la sección Órdenes"
                        )
                        InfoItem(
                            icon = Icons.Filled.ShoppingCart,
                            text = "Agrega productos al carrito desde el Catálogo"
                        )
                        InfoItem(
                            icon = Icons.Filled.Payment,
                            text = "Procede al pago desde el Carrito"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    action: QuickAction,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { navController.navigate(action.route) },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = action.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = action.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight * 0.9
            )
        }
    }
}

@Composable
fun InfoItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
    }
}