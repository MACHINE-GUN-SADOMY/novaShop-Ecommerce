package com.example.novasho_ecommerce.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.novasho_ecommerce.Model.CartItem
import com.example.novasho_ecommerce.Model.Order

class OrdersViewModel : ViewModel() {
    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: List<Order> get() = _orders.value

    fun addFromCart(cartItems: Map<String, Int>, resolvePrice: (String) -> Int) {
        if (cartItems.isEmpty()) return
        val items = cartItems.map { (id, qty) -> CartItem(id, qty) }
        val total = cartItems.entries.sumOf { (id, qty) -> resolvePrice(id) * qty }

        val newOrder = Order(
            id = System.currentTimeMillis().toInt(),
            items = items,
            total = total,
            createdAt = System.currentTimeMillis(),
            status = "Pending",
            subtotal = total / 1.18,
            tax = total - (total / 1.18)
        )
        _orders.value = listOf(newOrder) + _orders.value
    }
    fun clearOrders() {
        _orders.value = emptyList()
    }
}