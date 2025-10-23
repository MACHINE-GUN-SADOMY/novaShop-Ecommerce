package com.example.novasho_ecommerce.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    var items by mutableStateOf<Map<String, Int>>(emptyMap())
        private set

    fun add(productId: String) {
        val updatedItems = items.toMutableMap()
        updatedItems[productId] = (updatedItems[productId] ?: 0) + 1
        items = updatedItems
    }

    fun remove(productId: String) {
        val updatedItems = items.toMutableMap()
        val currentQty = updatedItems[productId] ?: 0
        if (currentQty > 1) {
            updatedItems[productId] = currentQty - 1
        } else {
            updatedItems.remove(productId)
        }
        items = updatedItems
    }

    fun clear() {
        items = emptyMap()
    }

   fun total(getProductPrice: (String) -> Int): Int {
        var sum = 0
        items.forEach { (id, qty) ->
            sum += getProductPrice(id) * qty
        }
        return sum
    }
}
