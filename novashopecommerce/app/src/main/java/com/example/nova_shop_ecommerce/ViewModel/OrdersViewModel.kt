package com.example.nova_shop_ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Model.Product
import com.example.nova_shop_ecommerce.Model.ShippingInfo
import com.example.nova_shop_ecommerce.Repository.OrdersRepository
import kotlinx.coroutines.launch

class OrdersViewModel : ViewModel() {

    private val repository = OrdersRepository()
    val orders = repository.orders

    fun addFromCart(
        cartItems: Map<String, Int>,
        resolveProduct: (String) -> Product,
        shipping: ShippingInfo
    ) {
        viewModelScope.launch {
            repository.addOrderFromCart(cartItems, resolveProduct, shipping)
        }
    }

    fun clearOrders() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
