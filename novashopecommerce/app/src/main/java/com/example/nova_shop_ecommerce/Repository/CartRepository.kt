package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CartRepository {

    private val mutex = Mutex()
    private val _items = MutableStateFlow<Map<String, Int>>(emptyMap())
    val items: StateFlow<Map<String, Int>> get() = _items

    suspend fun add(product: Product) {
        mutex.withLock {
            val updated = _items.value.toMutableMap()
            val key = product.id.toString()
            updated[key] = (updated[key] ?: 0) + 1
            _items.value = updated
        }
    }

    suspend fun remove(product: Product) {
        mutex.withLock {
            val updated = _items.value.toMutableMap()
            val key = product.id.toString()
            val qty = updated[key] ?: 0
            if (qty > 1) updated[key] = qty - 1 else updated.remove(key)
            _items.value = updated
        }
    }

    suspend fun clear() {
        mutex.withLock { _items.value = emptyMap() }
    }

    fun total(resolvePrice: (String) -> Double): Double {
        return _items.value.entries.sumOf { (id, qty) -> resolvePrice(id) * qty }
    }
}