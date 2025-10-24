package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.CartItem
import com.example.nova_shop_ecommerce.Model.Order
import com.example.nova_shop_ecommerce.Model.Product
import com.example.nova_shop_ecommerce.Model.ShippingInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class OrdersRepository {

    private val mutex = Mutex()
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    suspend fun addOrderFromCart(
        cartItems: Map<String, Int>,
        resolveProduct: (String) -> Product,
        shipping: ShippingInfo
    ) {
        if (cartItems.isEmpty()) return

        val items = cartItems.map { (id, qty) ->
            val product = resolveProduct(id)
            CartItem(productId = id, product = product, quanty = qty)
        }

        val subtotal = items.sumOf { it.product.price * it.quanty }
        val tax = subtotal * 0.19
        val total = subtotal + tax

        val order = Order(
            id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
            items = items,
            total = total,
            createdAt = System.currentTimeMillis(),
            status = "Pending",
            subtotal = subtotal,
            tax = tax,
            shipping = shipping
        )

        mutex.withLock { _orders.value = listOf(order) + _orders.value }
    }

    suspend fun clearAll() {
        mutex.withLock {
            _orders.value = emptyList()
        }
    }
}