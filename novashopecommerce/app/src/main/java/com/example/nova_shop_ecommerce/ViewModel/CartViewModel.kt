package com.example.nova_shop_ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nova_shop_ecommerce.Repository.CartRepository
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Model.Product
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val repository = CartRepository()
    val items = repository.items

    fun add(product: Product) {
        viewModelScope.launch { repository.add(product) }
    }

    fun remove(product: Product) {
        viewModelScope.launch { repository.remove(product) }
    }

    fun clear() {
        viewModelScope.launch { repository.clear() }
    }

    fun total(resolvePrice: (String) -> Double): Double = repository.total(resolvePrice)
}

